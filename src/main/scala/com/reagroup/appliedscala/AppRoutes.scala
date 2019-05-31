package com.reagroup.appliedscala

import cats.effect._
import org.http4s._
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class AppRoutes(fetchAllMoviesHandler: IO[Response[IO]],
                saveMovieHandler: Request[IO] => IO[Response[IO]]) extends Http4sDsl[IO] {

  val openRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case GET -> Root / "movies" => fetchAllMoviesHandler
    case req @ POST -> Root / "movies" => saveMovieHandler(req)
    case req @ POST -> Root / "movies" / LongVar(id) / "reviews" => ???
  }

}
