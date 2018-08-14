package com.reagroup.movies.api

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.{InMemRepository, MoviesController, MoviesService}
import org.http4s._

class AppRuntime() {

  private val repository = new InMemRepository

  private val service = new MoviesService(repository.getMovie, repository.saveMovie)

  private val controller = new MoviesController(service.getMovie, service.saveMovie)

  private val appRoutes = new AppRoutes(controller.getMovie, controller.saveMovie)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
