package com.reagroup.appliedscala.urls.effects

import cats.effect.IO
import com.reagroup.appliedscala.models._

trait MoviesRepository {

  def fetchMovie(movieId: MovieId): IO[Option[Movie]]

  def fetchAllMovies(): IO[Vector[Movie]]

  def saveMovie(movie: MovieToSave): IO[MovieId]

  def saveReview(movieId: MovieId, review: ReviewToSave): IO[ReviewId]

}
