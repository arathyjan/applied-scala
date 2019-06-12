package com.reagroup.appliedscala

import cats.effect._
import org.http4s._
import org.http4s.dsl.Http4sDsl

class AppRoutes(fetchAllMoviesHandler: IO[Response[IO]],
                fetchMovieHandler: Long => IO[Response[IO]],
                fetchEnrichedMovieHandler: Long => IO[Response[IO]],
                saveMovieHandler: Request[IO] => IO[Response[IO]]) extends Http4sDsl[IO] {

  object OptionalBooleanMatcher extends OptionalQueryParamDecoderMatcher[Boolean]("enriched")

  val openRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case GET -> Root / "movies" => fetchAllMoviesHandler
    case GET -> Root / "movie" / LongVar(id) :? OptionalBooleanMatcher(maybeEnriched) =>
      if (maybeEnriched.contains(true)) fetchEnrichedMovieHandler(id) else fetchMovieHandler(id)
    case GET -> Root / "hello" => Ok("hello World")
    case req @ POST -> Root / "movies" => saveMovieHandler(req)
    case req @ POST -> Root / "movies" / LongVar(id) / "reviews" => ???
  }

}
