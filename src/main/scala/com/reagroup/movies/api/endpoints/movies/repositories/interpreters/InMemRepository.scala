package com.reagroup.movies.api.endpoints.movies.repositories.interpreters

import cats.data.NonEmptyVector
import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.MoviesRepository
import com.reagroup.movies.api.models._

class InMemRepository extends MoviesRepository {

  override def getMovie(movieId: MovieId): IO[Option[Movie]] =
    IO.pure(Some(Movie("Batman Returns", "Best movie ever!", Vector.empty)))

  override def saveMovie(movie: NewMovie): IO[MovieId] =
    IO.pure(MovieId(12345))

  override def saveReviews(movieId: MovieId, reviews: NonEmptyVector[Review]): IO[NonEmptyVector[ReviewId]] =
    IO.pure(NonEmptyVector.one(ReviewId(999)))
}
