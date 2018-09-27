package com.reagroup.movies.api.endpoints.movies.repositories.effects

import cats.effect.IO
import com.reagroup.movies.api.models._

trait MoviesRepository {

  def getMovie(movieId: MovieId): IO[Option[Movie]]

  def saveMovie(movie: NewMovie): IO[MovieId]

}