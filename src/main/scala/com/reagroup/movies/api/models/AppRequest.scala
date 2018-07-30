package com.reagroup.movies.api.models

import cats.effect.IO
import io.circe.Json
import org.http4s.Request
import org.http4s.dsl.impl.LongVar
import org.http4s.dsl.io.{->, /, GET, POST, Root}

import io.circe.generic.auto._
import org.http4s.circe._

sealed trait AppRequest

case class GetMovieReq(id: MovieId) extends AppRequest

case class PostMovieReq(name: String, synopsis: String) extends AppRequest

case class InvalidReq(req: Request[IO], error: String) extends AppRequest

object AppRequest {

  def fromHttp4s: PartialFunction[Request[IO], IO[AppRequest]] = {
    case GET -> Root / "movies" / LongVar(id) => IO.pure(GetMovieReq(MovieId(id)))
    case req@POST -> Root / "movies" => req.as[Json].map(_.as[PostMovieReq] match {
      case Left(e) => InvalidReq(req, e.getMessage())
      case Right(r) => r
    })
  }

}