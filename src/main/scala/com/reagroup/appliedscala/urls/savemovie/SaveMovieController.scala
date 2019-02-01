package com.reagroup.appliedscala.urls.savemovie

import cats.data.Validated._
import cats.effect.IO
import com.reagroup.appliedscala.models._
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class SaveMovieController(service: SaveMovieService) extends Http4sDsl[IO] {

  def apply(req: Request[IO]): IO[Response[IO]] =
    for {
      newMovieReq <- req.as[NewMovieRequest]
      errorOrNewMovieId <- service.save(newMovieReq).attempt
      resp <- errorOrNewMovieId match {
        case Right(Valid(newMovieId)) => Created(newMovieId.asJson)
        case Right(Invalid(errors)) => BadRequest(errors.asJson)
        case Left(e) => encodeError(e)
      }
    } yield resp

  private def encodeError(e: Throwable): IO[Response[IO]] =
    InternalServerError(Json.obj("error" -> e.getMessage.asJson))

}
