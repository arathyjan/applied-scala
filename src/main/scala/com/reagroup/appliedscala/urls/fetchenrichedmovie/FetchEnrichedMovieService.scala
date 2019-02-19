package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.EnrichmentFailure

class FetchEnrichedMovieService(fetchMovie: MovieId => IO[Option[Movie]], fetchStarRating: String => IO[Option[StarRating]]) {

  def fetch(movieId: MovieId): IO[Option[EnrichedMovie]] = ???
}
