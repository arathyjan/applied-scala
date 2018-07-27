package com.reagroup.movies.api

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.{InMemRepository, MoviesController}
import org.http4s.HttpService

class AppRuntime() {

  private val repository = new InMemRepository

  private val controller = new MoviesController(repository)

  private val appRoutes = new AppRoutes(controller.apply)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
