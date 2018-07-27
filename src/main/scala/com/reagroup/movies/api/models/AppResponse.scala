package com.reagroup.movies.api.models

import cats.effect.IO

sealed trait AppResponse

case class GetMovieResp(optMovie: Option[Movie]) extends AppResponse

case class PostMovieResp(id: MovieId) extends AppResponse

case object RouteNotFoundResp extends AppResponse

case class ErrorResp(msg: String) extends AppResponse

object AppResponse {

  def fromIO[A](ioA: IO[A], f: A => AppResponse): IO[AppResponse] =
    ioA.attempt.map {
      case Left(e) => ErrorResp(e.getMessage)
      case Right(a) => f(a)
    }

}