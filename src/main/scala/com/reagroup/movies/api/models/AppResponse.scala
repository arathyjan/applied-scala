package com.reagroup.movies.api.models

import cats.effect.IO

sealed trait AppResponse

case class MovieResp(optMovie: Option[Movie]) extends AppResponse

case class NewMovieResp(id: MovieId) extends AppResponse

case class ErrorResp(error: AppError) extends AppResponse

object AppResponse {

  def toAppResp[A](ioA: IO[A], f: A => AppResponse): IO[AppResponse] =
    ioA.attempt.map {
      case Left(e) => ErrorResp(ServerError(e))
      case Right(a) => f(a)
    }

}