package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import cats.implicits._

class FetchEnrichedMovieService(fetchMovie: MovieId => IO[Option[Movie]],
                                fetchStarRating: String => IO[Option[StarRating]]) {

  /**
    * In order to construct an `EnrichedMovie`, we need to first get a `Movie` and a `StarRating`.
    * We can do so using the functions that are passed in as dependencies.
    *
    * For the purpose of this exercise, let's raise an `EnrichmentFailure` if the `StarRating` does not exist.
    *
    * Hint: Pattern match on `Option` if you're stuck!
    */
  def fetch(movieId: MovieId): IO[Option[EnrichedMovie]] = {

    for {
      mayBeMovie <- fetchMovie(movieId)
      mayBeEnrichedMovie <- mayBeMovie match {
        case Some(movie) => enrchichedMovieWithRating(movieId, movie).map(x => Some(x))
        case None => IO.pure(None)
      }
    } yield mayBeEnrichedMovie

  }

  private def enrchichedMovieWithRating(movieId: MovieId, movie: Movie): IO[EnrichedMovie] = {
    fetchStarRating(movie.name).flatMap {
      case Some(rating) => IO.pure(EnrichedMovie(movie, rating))
      case None => IO.raiseError(EnrichmentFailure(movieId))
    }
  }

}