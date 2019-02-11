package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.EnrichmentFailure

class FetchEnrichedMovieService(fetchMovie: MovieId => IO[Option[Movie]], fetchStarRating: String => IO[Option[StarRating]]) {

  def fetch(movieId: MovieId): IO[Option[EnrichedMovie]] =
    for {
      optMovie <- fetchMovie(movieId)
      optStar <- optMovie match {
        case Some(movie) => fetchStarRating(movie.name)
        case None => IO(None)
      }
      enriched <- (optMovie, optStar) match {
        case (Some(movie), Some(star)) => IO(Some(EnrichedMovie(movie, star)))
        case (Some(movie), None) => IO.raiseError(EnrichmentFailure(movie))
        case (None, _) => IO.pure(None)
      }
    } yield enriched

}
