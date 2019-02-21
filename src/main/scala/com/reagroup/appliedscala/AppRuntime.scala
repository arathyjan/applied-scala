package com.reagroup.appliedscala

import cats.effect.IO
import com.reagroup.appliedscala.urls.effects.PostgresqlRepository
import com.reagroup.appliedscala.urls.fetchallmovies.{FetchAllMoviesController, FetchAllMoviesService}
import com.reagroup.appliedscala.urls.fetchenrichedmovie.{FetchEnrichedMovieController, FetchEnrichedMovieService, Http4sStarRatingsRepository}
import com.reagroup.appliedscala.urls.fetchmovie.{FetchMovieController, FetchMovieService}
import com.reagroup.appliedscala.urls.savemovie.{SaveMovieController, SaveMovieService}
import com.reagroup.appliedscala.urls.savereview.{SaveReviewController, SaveReviewService}
import org.http4s._

class AppRuntime() {

  private val pgsqlRepo = PostgresqlRepository(sys.env)

  private val fetchAllMoviesController: FetchAllMoviesController = {
    val fetchAllMoviesService: FetchAllMoviesService = new FetchAllMoviesService(pgsqlRepo.fetchAllMovies)
    new FetchAllMoviesController(fetchAllMoviesService.fetchAll)
  }

  private val appRoutes = new AppRoutes(fetchAllMoviesController)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
