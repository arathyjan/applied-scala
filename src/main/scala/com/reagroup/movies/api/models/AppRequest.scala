package com.reagroup.movies.api.models

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.Request
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.impl.LongVar
import org.http4s.dsl.io._

sealed trait AppRequest

case class GetMovieReq(id: MovieId) extends AppRequest

case class PostMovieReq(name: String, synopsis: String) extends AppRequest

case class InvalidReq(error: AppError) extends AppRequest

object AppRequest {

  def fromHttp4s: Request[IO] => IO[AppRequest] = {
    case GET -> Root / "movies" / LongVar(id) => IO.pure(GetMovieReq(MovieId(id)))
    case req@POST -> Root / "movies" => req.attemptAs[PostMovieReq].fold(e => InvalidReq(InvalidMovieCreationError(e.getMessage())), identity)
    case req => IO.pure(InvalidReq(UnknownRouteError))
  }

}