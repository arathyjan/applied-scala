package com.reagroup.movies.api.endpoints.movies.services.interpreters

import cats.data._
import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.{MoviesRepository, StarRatingsRepository}
import com.reagroup.movies.api.endpoints.movies.services.{InvalidNewMovieErr, InvalidReviewErr, NewMovieValidator, ReviewValidator}
import com.reagroup.movies.api.endpoints.movies.services.effects.MoviesServiceEffects
import com.reagroup.movies.api.models._
import cats.implicits._

class MoviesService(moviesRepo: MoviesRepository, starRatingsRepo: StarRatingsRepository) extends MoviesServiceEffects {


  override def get(movieId: MovieId): IO[Option[EnrichedMovie]] =
    for {
      optMovie <- moviesRepo.getMovie(movieId)
      optStar <- optMovie match {
        case Some(movie) => starRatingsRepo.getStarRating(movie.name)
        case None => IO(None)
      }
      enriched <- (optMovie, optStar) match {
        case (Some(movie), Some(star)) => IO(Some(EnrichedMovie(movie, star)))
        case (Some(movie), None) => IO.raiseError(new Throwable(s"Failed to enrich movie ${movie.name}"))
        case (None, _) => IO.pure(None)
      }
    } yield enriched

  override def save(newMovieReq: NewMovie): IO[ValidatedNel[InvalidNewMovieErr, MovieId]] =
    NewMovieValidator.validate(newMovieReq).traverse(moviesRepo.saveMovie)

  override def saveReview(movieId: MovieId, review: Review): IO[ValidatedNel[InvalidReviewErr, ReviewId]] =
    ReviewValidator.validate(review).traverse(moviesRepo.saveReview(movieId, _))

}
