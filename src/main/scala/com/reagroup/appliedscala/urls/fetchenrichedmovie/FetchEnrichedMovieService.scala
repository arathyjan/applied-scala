package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.EnrichmentFailure

class FetchEnrichedMovieService(fetchMovie: MovieId => IO[Option[Movie]], fetchStarRating: String => IO[Option[StarRating]]) {

  /**
    * In order to construct an `EnrichedMovie`, we need to first get a `Movie` and a `StarRating`.
    * We can do so using the functions that are passed in as dependencies.
    *
    * For the purpose of this exercise, let's raise an `EnrichmentFailure` if the `StarRating` does not exist.
    */
  def fetch(movieId: MovieId): IO[Option[EnrichedMovie]] = ???
}
