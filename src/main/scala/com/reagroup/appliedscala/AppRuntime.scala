package com.reagroup.appliedscala

import cats.effect.IO
import com.reagroup.appliedscala.urls.repositories.{Http4sStarRatingsRepository, PostgresqlRepository}
import com.reagroup.appliedscala.urls.fetchallmovies.{FetchAllMoviesController, FetchAllMoviesService}
import com.reagroup.appliedscala.urls.fetchenrichedmovie.{FetchEnrichedMovieController, FetchEnrichedMovieService}
import com.reagroup.appliedscala.urls.fetchmovie.{FetchMovieController, FetchMovieService}
import com.reagroup.appliedscala.urls.savemovie.{SaveMovieController, SaveMovieService}
import com.reagroup.appliedscala.urls.savereview.{SaveReviewController, SaveReviewService}
import org.http4s._

class AppRuntime() {

  /**
    * This is the real repository that talks to Postgresql
    */
  private val pgsqlRepo = PostgresqlRepository(sys.env)

  /**
    * This is where we instantiate our `Service` and `Controller` for each endpoint.
    * We will need to write a similar block for each endpoint we write.
    */
  private val fetchAllMoviesController: FetchAllMoviesController = {
    val fetchAllMoviesService: FetchAllMoviesService = new FetchAllMoviesService(pgsqlRepo.fetchAllMovies)
    new FetchAllMoviesController(fetchAllMoviesService.fetchAll)
  }

  private val fetchMovieController: FetchMovieController = {
    val service: FetchMovieService = new FetchMovieService(pgsqlRepo.fetchMovie)
    new FetchMovieController(service.fetch)
  }

  private val fetchEnrichedMovieController: FetchEnrichedMovieController = {
    val http4sStarRatingsRepository = new Http4sStarRatingsRepository
    val service: FetchEnrichedMovieService = new FetchEnrichedMovieService(pgsqlRepo.fetchMovie, http4sStarRatingsRepository.apply)
    new FetchEnrichedMovieController(service.fetch)
  }

  private val saveMovieController: SaveMovieController = {
    val service: SaveMovieService = new SaveMovieService(pgsqlRepo.saveMovie)
    new SaveMovieController(service.save)
  }

  private val saveReviewController: SaveReviewController = {
    val service: SaveReviewService = new SaveReviewService(pgsqlRepo.saveReview)
    new SaveReviewController(service.save)
  }

  private val appRoutes = new AppRoutes(fetchAllMoviesController, fetchMovieController, fetchEnrichedMovieController, saveMovieController, saveReviewController)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
