package com.reagroup.movies.api.endpoints.movies.services.interpreters

import cats.data._
import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.{MoviesRepository, StarRatingsRepository}
import com.reagroup.movies.api.endpoints.movies.services.{InvalidNewMovieErr, InvalidReviewErr, NewMovieValidator, ReviewValidator}
import com.reagroup.movies.api.endpoints.movies.services.effects.MoviesServiceEffects
import com.reagroup.movies.api.models._
import cats.implicits._

class MoviesService(moviesRepo: MoviesRepository, starRatingsRepo: StarRatingsRepository) extends MoviesServiceEffects {

  // TODO: Extension - Handle no StarRating gracefully
  override def get(movieId: MovieId): IO[EnrichedMovie] = {

    for {
      optMovie <- moviesRepo.getMovie(movieId)
      optStarRating <- optMovie match {
        case Some(movie) => starRatingsRepo.getStarRating(movie.name)
        case None => IO(None)
      }
      enriched <- (optMovie, optStarRating) match {
        case (Some(movie), Some(star)) => IO(EnrichedMovie(movie, star))
        case _ => IO.raiseError(new Throwable(s"Cannot enrich movie: $movieId"))
      }
    } yield enriched
  }

  override def save(newMovieReq: NewMovie): IO[ValidatedNel[InvalidNewMovieErr, MovieId]] =
    NewMovieValidator.validate(newMovieReq).traverse(moviesRepo.saveMovie)

  override def saveReviews(movieId: MovieId, reviews: NonEmptyVector[Review]): IO[IorNel[InvalidReviewErr, NonEmptyVector[ReviewId]]] =
    reviews.map(ReviewValidator.validate)
      .map(_.map(NonEmptyVector.one))
      .map(_.toIor)
      .reduce
      .traverse(moviesRepo.saveReviews(movieId, _))

}