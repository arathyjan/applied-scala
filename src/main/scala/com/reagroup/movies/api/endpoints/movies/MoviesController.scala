package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.services.effects.MoviesServiceEffects
import com.reagroup.movies.api.models._
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import cats.data._
import cats.data.Validated._
import cats.implicits._

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

  def saveReviews(movieId: Long, req: Request[IO]): IO[Response[IO]] =
    for {
      reviews <- req.as[NonEmptyVector[Review]]
      errsOrIds <- service.saveReviews(MovieId(movieId), reviews)
      _ <- IO(println(errsOrIds))
      resp <- errsOrIds match {
        case Ior.Left(errs) => BadRequest(errs.toList.asJson)
        case Ior.Right(ids) => Created(ids.toVector.asJson)
        case Ior.Both(errs, ids) => IO(println(s"Found errors: ${errs.toList.asJson.noSpaces}")) >> Created(ids.toVector.asJson)
      }
    } yield resp

  private def encodeError(e: Throwable): IO[Response[IO]] =
    InternalServerError(Json.obj("error" -> e.getMessage.asJson))

}
