package com.reagroup.movies.api.endpoints.movies.services.effects

import cats.data.{IorNel, NonEmptyVector, ValidatedNel}
import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.services.{InvalidNewMovieErr, InvalidReviewErr}
import com.reagroup.movies.api.models._

trait MoviesServiceEffects {

  def get(movieId: MovieId): IO[Option[EnrichedMovie]]

  def save(newMovieReq: NewMovie): IO[ValidatedNel[InvalidNewMovieErr, MovieId]]

  def saveReviews(movieId: MovieId, reviews: NonEmptyVector[Review]): IO[IorNel[InvalidReviewErr, NonEmptyVector[ReviewId]]]

}
