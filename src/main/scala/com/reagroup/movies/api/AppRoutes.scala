package com.reagroup.movies.api

import cats.effect._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Request, Response}

class AppRoutes(fetchMovie: Long => IO[Response[IO]],
                saveMovie: Request[IO] => IO[Response[IO]]) extends Http4sDsl[IO] {

  val openRoutes = HttpService[IO] {
    case GET -> Root / "movies" / LongVar(id) => fetchMovie(id)
    case req@POST -> Root / "movies" => saveMovie(req)
  }

}