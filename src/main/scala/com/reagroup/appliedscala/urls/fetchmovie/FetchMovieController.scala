package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class FetchMovieController(fetchMovie: MovieId => IO[Option[Movie]]) extends Http4sDsl[IO] {

  def apply(movieId: Long): IO[Response[IO]] = for {
    errorOrMovie <- fetchMovie(MovieId(movieId)).attempt
    resp <- errorOrMovie match {
      case Right(Some(movie)) => Ok(movie.asJson)
      case Right(None) => NotFound()
      case Left(e) => ErrorHandler(e)
    }
  } yield resp

}
