package com.reagroup.movies.api

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.interpreters.{Http4sStarRatingsService, InMemRepository, PostgresqlRepository}
import com.reagroup.movies.api.endpoints.movies.MoviesController
import com.reagroup.movies.api.endpoints.movies.services.interpreters.MoviesService
import org.http4s._

class AppRuntime() {

  private val repository = if (sys.env.contains("DATABASE_HOST")) {
    PostgresqlRepository(sys.env)
  } else {
    new InMemRepository
  }

  private val ratingsRepo = new Http4sStarRatingsService

  private val service = new MoviesService(repository, ratingsRepo)

  private val controller = new MoviesController(service)

  private val appRoutes = new AppRoutes(controller)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
