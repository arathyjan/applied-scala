package com.reagroup.appliedscala.urls.effects

import cats.effect.IO
import com.reagroup.appliedscala.models._

class InMemRepository extends MoviesRepository {

  override def fetchMovie(movieId: MovieId): IO[Option[Movie]] =
    IO.pure(Some(Movie("Batman Returns", "Best movie ever!", Vector.empty)))

  override def saveMovie(movie: MovieToSave): IO[MovieId] =
    IO.pure(MovieId(12345))

  override def saveReview(movieId: MovieId, review: ReviewToSave): IO[ReviewId] = IO.pure(ReviewId(1000))
}
