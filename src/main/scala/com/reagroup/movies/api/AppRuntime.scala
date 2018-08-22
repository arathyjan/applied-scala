package com.reagroup.movies.api

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.interpreters.InMemRepository
import com.reagroup.movies.api.endpoints.movies.MoviesController
import com.reagroup.movies.api.endpoints.movies.services.interpreters.MoviesService
import org.http4s._

class AppRuntime() {

  private val repository = new InMemRepository

  private val service = new MoviesService(repository)

  private val controller = new MoviesController(service)

  private val appRoutes = new AppRoutes(controller)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
