package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import cats.data.Validated._
import com.reagroup.movies.api.endpoints.movies.services.effects.MoviesServiceEffects
import com.reagroup.movies.api.models._
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class MoviesController(service: MoviesServiceEffects) extends Http4sDsl[IO] {

  def getMovie(movieId: Long): IO[Response[IO]] = for {
    errorOrMovie <- service.get(MovieId(movieId)).attempt
    resp <- errorOrMovie match {
      case Right(enrichedMovie) => Ok(enrichedMovie.asJson)
      case Left(e) => encodeError(e)
    }
  } yield resp

  def saveMovie(req: Request[IO]): IO[Response[IO]] =
    for {
      newMovieReq <- req.as[NewMovie]
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
