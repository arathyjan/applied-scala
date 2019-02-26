package com.reagroup.appliedscala.urls

import cats.effect.IO
import com.reagroup.appliedscala.models.errors.{AppError, EnrichmentFailure}
import io.circe.Json
import io.circe.syntax._
import org.http4s.Response
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

object ErrorHandler extends Http4sDsl[IO] {

  def apply(e: Throwable): IO[Response[IO]] =
    e match {
      case err: AppError => encodeAppError(err)
      case err => InternalServerError(Json.obj("error" -> s"Unexpected error has occurred: ${err.getMessage}".asJson))
    }

  private def encodeAppError(appError: AppError): IO[Response[IO]] =
    appError match {
      case EnrichmentFailure(movieId) => InternalServerError(Json.obj("error" -> s"Failed to enrich movieId: ${movieId.value}".asJson))
    }

}
