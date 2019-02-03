package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class FetchMovieController(service: FetchMovieService) extends Http4sDsl[IO] {

  def apply(movieId: Long): IO[Response[IO]] = for {
    errorOrMovie <- service.fetchMovie(MovieId(movieId)).attempt
    resp <- errorOrMovie match {
      case Right(Some(movie)) => Ok(movie.asJson)
      case Right(None) => NotFound()
      case Left(e) => encodeError(e)
    }
  } yield resp

  private def encodeError(e: Throwable): IO[Response[IO]] =
    InternalServerError(Json.obj("error" -> e.getMessage.asJson))

}
