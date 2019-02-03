package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._

class FetchEnrichedMovieService(repo: FetchEnrichedMovieRepository, starRatingsRepo: StarRatingsRepository) {

  def fetchMovie(movieId: MovieId): IO[Option[EnrichedMovie]] =
    for {
      optMovie <- repo.fetchMovie(movieId)
      optStar <- optMovie match {
        case Some(movie) => starRatingsRepo.fetchStarRating(movie.name)
        case None => IO(None)
      }
      enriched <- (optMovie, optStar) match {
        case (Some(movie), Some(star)) => IO(Some(EnrichedMovie(movie, star)))
        case (Some(movie), None) => IO.raiseError(new Throwable(s"Failed to enrich movie ${movie.name}"))
        case (None, _) => IO.pure(None)
      }
    } yield enriched

}
