package com.reagroup.appliedscala

import cats.effect.IO
import com.reagroup.appliedscala.urls.effects.{InMemRepository, PostgresqlRepository}
import com.reagroup.appliedscala.urls.fetchenrichedmovie.{FetchEnrichedMovieController, FetchEnrichedMovieService, Http4sStarRatingsService}
import com.reagroup.appliedscala.urls.fetchmovie.{FetchMovieController, FetchMovieService}
import com.reagroup.appliedscala.urls.savemovie.{SaveMovieController, SaveMovieService}
import com.reagroup.appliedscala.urls.savereview.{SaveReviewController, SaveReviewService}
import org.http4s._

class AppRuntime() {

  private val repository = if (sys.env.contains("DATABASE_HOST")) {
    PostgresqlRepository(sys.env)
  } else {
    new InMemRepository
  }

  private val ratingsRepo = new Http4sStarRatingsService

  private val fetchMovie = new FetchMovieController(new FetchMovieService(repository.fetchMovie))

  private val fetchEnrichedMovie = new FetchEnrichedMovieController(new FetchEnrichedMovieService(repository.fetchMovie, ratingsRepo.fetchStarRating))

  private val saveMovie = new SaveMovieController(new SaveMovieService(repository.saveMovie))

  private val saveReview = new SaveReviewController(new SaveReviewService(repository.saveReview))

  private val appRoutes = new AppRoutes(fetchMovie, fetchEnrichedMovie, saveMovie, saveReview)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
