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

  private val ratingsRepo = new Http4sStarRatingsRepository

  private val fetchMovie = new FetchMovieController(new FetchMovieService(pgsqlRepo.fetchMovie))

  private val fetchEnrichedMovie = new FetchEnrichedMovieController(new FetchEnrichedMovieService(pgsqlRepo.fetchMovie, ratingsRepo.apply))

  private val fetchAllMovies = new FetchAllMoviesController(new FetchAllMoviesService(() => pgsqlRepo.fetchAllMovies()))

  private val saveMovie = new SaveMovieController(new SaveMovieService(pgsqlRepo.saveMovie))

  private val saveReview = new SaveReviewController(new SaveReviewService(pgsqlRepo.saveReview))

  private val appRoutes = new AppRoutes(fetchMovie, fetchEnrichedMovie, fetchAllMovies, saveMovie, saveReview)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
