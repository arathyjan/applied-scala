package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.models._
import io.circe.{Encoder, Json}
import org.http4s._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class MoviesController(service: MoviesService) extends Http4sDsl[IO] {

  def getMovie(movieId: Long): IO[Response[IO]] = {
    val optMovie = service.get(MovieId(movieId))
    attemptIO(optMovie, (x: Option[Movie]) => x match {
      case Some(movie) => Ok(movie.asJson)
      case None => NotFound()
    })
  }

  def saveMovie(req: Request[IO]): IO[Response[IO]] =
    for {
      newMovieReq <- req.as[NewMovie]
      newMovieId <- service.save(newMovieReq)
      resp <- Created(newMovieId.asJson)
    } yield resp

  private def attemptIO[A](ioA: IO[A], f: A => IO[Response[IO]]): IO[Response[IO]] =
    ioA.attempt.flatMap {
      case Right(a) => f(a)
      case Left(e) => InternalServerError(Json.obj("error" -> e.getMessage.asJson))
    }

}
