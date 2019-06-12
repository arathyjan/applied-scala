package com.reagroup.appliedscala

import cats.data.Kleisli
import cats.effect.IO
import cats.effect.ContextShift
import cats.effect.Timer
import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.config.Config
import com.reagroup.appliedscala.models.MovieId
import com.reagroup.appliedscala.urls.repositories.{Http4sStarRatingsRepository, PostgresqlRepository}
import com.reagroup.appliedscala.urls.diagnostics.Diagnostics
import com.reagroup.appliedscala.urls.fetchallmovies.{FetchAllMoviesController, FetchAllMoviesService}
import com.reagroup.appliedscala.urls.fetchenrichedmovie.{FetchEnrichedMovieController, FetchEnrichedMovieService}
import com.reagroup.appliedscala.urls.fetchmovie.{FetchMovieController, FetchMovieService}
import com.reagroup.appliedscala.urls.savemovie.{SaveMovieController, SaveMovieService}
import com.reagroup.appliedscala.urls.savereview.{SaveReviewController, SaveReviewService}
import org.http4s._
import org.http4s.client.Client

class AppRuntime(config: Config, httpClient: Client[IO], contextShift: ContextShift[IO], timer: Timer[IO]) {

  /**
    * This is the repository that talks to Postgresql
    */
  private val pgsqlRepo = PostgresqlRepository(config.databaseConfig)
  private val http4sStarRatingsRepository = new Http4sStarRatingsRepository(httpClient, config.omdbApiKey)
  /**
    * This is where we instantiate our `Service` and `Controller` for each endpoint.
    * We will need to write a similar block for each endpoint we write.
    */
  private val fetchAllMoviesController: FetchAllMoviesController = {
    val fetchAllMoviesService: FetchAllMoviesService = new FetchAllMoviesService(pgsqlRepo.fetchAllMovies)
    new FetchAllMoviesController(fetchAllMoviesService.fetchAll)
  }

  private val fetchMovieController: FetchMovieController = {
    val fetchMovieService: FetchMovieService = new FetchMovieService(pgsqlRepo.fetchMovie)
    new FetchMovieController(fetchMovieService.fetch)
  }

  private val fetchEnrichedMovieController: FetchEnrichedMovieController = {
    val service: FetchEnrichedMovieService = new FetchEnrichedMovieService(pgsqlRepo.fetchMovie, http4sStarRatingsRepository.apply)
    new FetchEnrichedMovieController(service.fetch)
  }

  private val appRoutes = new AppRoutes(
    fetchAllMoviesHandler = fetchAllMoviesController.fetchAll,
    fetchMovieHandler = fetchMovieController.fetch,
    fetchEnrichedMovieHandler = fetchEnrichedMovieController.fetch,
    saveReviewHandler = saveReviewController.save,
    saveMovieHandler = saveMovieController.save
  )

  private val saveMovieController: SaveMovieController = {
    val saveMovieService: SaveMovieService = new SaveMovieService(pgsqlRepo.saveMovie)
    new SaveMovieController(saveMovieService.save)
  }

  private val saveReviewController: SaveReviewController = {
    val savereviewService: SaveReviewService = new SaveReviewService(pgsqlRepo.saveReview, pgsqlRepo.fetchMovie)
    new SaveReviewController(savereviewService.save)
  }

  private val diagnosticRoutes = Diagnostics(config, pgsqlRepo)(contextShift, timer)
  /*
   * All routes that make up the application are exposed by AppRuntime here.
   */

  private val allRoutes = appRoutes.openRoutes.combineK(diagnosticRoutes.routes)
//  def routes: HttpApp[IO] = HttpApp((req: Request[IO]) => appRoutes.openRoutes(req).getOrElse(Response[IO](status = Status.NotFound)))
  def routes: HttpApp[IO] = HttpApp((req: Request[IO]) => allRoutes(req).getOrElse(Response[IO](status = Status.NotFound)))

}
