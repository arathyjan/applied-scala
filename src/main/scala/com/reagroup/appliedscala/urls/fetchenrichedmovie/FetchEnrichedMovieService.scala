package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.EnrichmentFailure

class FetchEnrichedMovieService(fetchMovie: MovieId => IO[Option[Movie]],
                                fetchStarRating: String => IO[Option[StarRating]]) {

  /**
    * In order to construct an `EnrichedMovie`, we need to first get a `Movie` and a `StarRating`.
    * We can do so using the functions that are passed in as dependencies.
    *
    * For the purpose of this exercise, let's raise an `EnrichmentFailure` if the `StarRating` does not exist.
    */
  def fetch(movieId: MovieId): IO[Option[EnrichedMovie]] =
    for {
      optMovie <- fetchMovie(movieId)
      optStarRating <- optMovie match {
        case Some(Movie(name, _, _)) => fetchStarRating(name)
        case None => IO.pure(None)
      }
      enrichedMovie <- (optMovie, optStarRating) match {
        case (Some(movie), Some(rating)) => IO.pure(Some(EnrichedMovie(movie, rating)))
        case (Some(movie), None) => IO.raiseError(EnrichmentFailure(movie))
        case (None, _) => IO.pure(None)
      }
    } yield enrichedMovie
}
