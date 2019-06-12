package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class FetchEnrichedMovieController(fetchEnrichedMovie: MovieId => IO[Option[EnrichedMovie]]) extends Http4sDsl[IO] {

  def fetch(movieId: Long): IO[Response[IO]] =
    fetchEnrichedMovie(MovieId(movieId)).attempt.flatMap {
      case Right(Some(enrichedMovie)) => Ok(enrichedMovie)
      case Right(None) => NotFound()
      case Left(e) => ErrorHandler(e)
    }
}
