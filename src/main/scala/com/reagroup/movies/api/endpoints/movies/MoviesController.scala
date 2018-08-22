package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.services.algebras.MoviesServiceActions
import com.reagroup.movies.api.models._
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class MoviesController(service: MoviesServiceActions) extends Http4sDsl[IO] {

  def getMovie(movieId: Long): IO[Response[IO]] = for {
    errorOrMovie <- service.get(MovieId(movieId)).attempt
    resp <- errorOrMovie match {
      case Right(Some(movie)) => Ok(movie.asJson)
      case Right(None) => NotFound()
      case Left(e) => encodeError(e)
    }
  } yield resp

  def saveMovie(req: Request[IO]): IO[Response[IO]] =
    for {
      newMovieReq <- req.as[NewMovie]
      errorOrNewMovieId <- service.save(newMovieReq).attempt
      resp <- errorOrNewMovieId match {
        case Right(newMovieId) => Created(newMovieId.asJson)
        case Left(e) => encodeError(e)
      }
    } yield resp

  private def encodeError(e: Throwable): IO[Response[IO]] =
    InternalServerError(Json.obj("error" -> e.getMessage.asJson))

}
