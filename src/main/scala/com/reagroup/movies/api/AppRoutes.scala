package com.reagroup.movies.api

import cats.effect._
import com.reagroup.movies.api.endpoints.movies.MoviesController
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Request, Response}

class AppRoutes(controller: MoviesController) extends Http4sDsl[IO] {

  val openRoutes = HttpService[IO] {
    case GET -> Root / "movies" / LongVar(id) => controller.getMovie(id)
    case req@POST -> Root / "movies" => controller.saveMovie(req)
    case req@POST -> Root / "movies" / LongVar(id) / "reviews" => controller.saveReviews(id, req)
  }

}