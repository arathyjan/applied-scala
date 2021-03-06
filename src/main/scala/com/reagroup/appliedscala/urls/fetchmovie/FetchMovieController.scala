package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.ErrorHandler
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class FetchMovieController(fetchMovie: MovieId => IO[Option[Movie]]) extends Http4sDsl[IO] {

  def fetch(movieId: Long): IO[Response[IO]] = for {
    errorOrMovieOption <- fetchMovie(MovieId(movieId)).attempt
    resp <- errorOrMovieOption match {
      case Right(Some(movie)) => Ok(movie)
      case Right(None) => NotFound()
      case Left(e) => ErrorHandler(e)
    }
  } yield resp
}
