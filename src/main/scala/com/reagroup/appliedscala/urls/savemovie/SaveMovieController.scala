package com.reagroup.appliedscala.urls.savemovie

import cats.data.Validated._
import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class SaveMovieController(saveNewMovie: NewMovieRequest => IO[ValidatedNel[MovieValidationError, MovieId]]) extends Http4sDsl[IO] {

  /**
    * If we have an `Invalid(NonEmptyList(errors))`, return a Json with the following shape:
    *
    * {
    *   "errors": [
    *     {
    *       "type": "MOVIE_NAME_TOO_SHORT"
    *     },
    *     {
    *       "type": "MOVIE_SYNOPSIS_TOO_SHORT"
    *     }
    *   ]
    * }
    *
    * Hint: You can use the `BadRequest(...)` constructor to return a 403 response when there are errors.
    */
  def save(req: Request[IO]): IO[Response[IO]] =
  {
//    val newMovieReqIO: IO[NewMovieRequest] = req.as[NewMovieRequest]
//    val a: IO[Either[Throwable, ValidatedNel[MovieValidationError, MovieId]]] = newMovieReqIO.flatMap(saveNewMovie).attempt
//    val c: IO[Response[IO]] = a.flatMap {
//      case Right(Valid(movieId)) => Created(movieId)
//      case Right(Invalid(movieValidationError)) => BadRequest(Json.obj("errors" -> movieValidationError.asJson))
//      case Left(e) => ErrorHandler(e)
//    }

    for {
      newMovieReq <- req.as[NewMovieRequest]
      failureOrErrorOrMovieId <- saveNewMovie(newMovieReq).attempt
      resp <- failureOrErrorOrMovieId match {
        case Right(Valid(movieId)) => Created(movieId.asJson)
        case Right(Invalid(movieValidationError)) => BadRequest(Json.obj("errors" -> movieValidationError.asJson))
        case Left(e) => ErrorHandler(e)
      }
    } yield resp
  }


}
