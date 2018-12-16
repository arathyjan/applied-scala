package com.reagroup.movies.api.endpoints.movies.repositories.effects

import cats.data.NonEmptyVector
import cats.effect.IO
import com.reagroup.movies.api.models._

trait MoviesRepository {

  def getMovie(movieId: MovieId): IO[Option[Movie]]

  def saveMovie(movie: NewMovie): IO[MovieId]

  def saveReviews(movieId: MovieId, reviews: NonEmptyVector[Review]): IO[NonEmptyVector[ReviewId]]
  def saveReview(movieId: MovieId, review: Review): IO[ReviewId]

}
