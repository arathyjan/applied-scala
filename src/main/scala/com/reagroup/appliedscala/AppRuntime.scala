package com.reagroup.appliedscala

import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.config.Config
import com.reagroup.appliedscala.urls.repositories.{Http4sStarRatingsRepository, PostgresqlRepository}
import com.reagroup.appliedscala.urls.diagnostics.Diagnostics
import com.reagroup.appliedscala.urls.fetchallmovies.{FetchAllMoviesController, FetchAllMoviesService}
import com.reagroup.appliedscala.urls.fetchenrichedmovie.{FetchEnrichedMovieController, FetchEnrichedMovieService}
import com.reagroup.appliedscala.urls.fetchmovie.{FetchMovieController, FetchMovieService}
import com.reagroup.appliedscala.urls.savemovie.{SaveMovieController, SaveMovieService}
import com.reagroup.appliedscala.urls.savereview.{SaveReviewController, SaveReviewService}
import org.http4s._

class AppRuntime(config: Config) {

  /**
    * This is the real repository that talks to Postgresql
    */
  private val pgsqlRepo = PostgresqlRepository(config.databaseConfig)

  /**
    * This is where we instantiate our `Service` and `Controller` for each endpoint.
    * We will need to write a similar block for each endpoint we write.
    */
  private val fetchAllMoviesController: FetchAllMoviesController = {
    val fetchAllMoviesService: FetchAllMoviesService = new FetchAllMoviesService(pgsqlRepo.fetchAllMovies)
    new FetchAllMoviesController(fetchAllMoviesService.fetchAll)
  }

  private val appRoutes = new AppRoutes(
    fetchAllMoviesHandler = fetchAllMoviesController(),
    saveMoviesHandler = _ => IO(Response[IO](status = Status.NotImplemented))
  )

  private val diagnosticRoutes = Diagnostics(config, pgsqlRepo)

  val routes: HttpService[IO] = appRoutes.openRoutes <+> diagnosticRoutes

}
