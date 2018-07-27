package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.models.{Movie, MovieId}

trait MoviesRepository {

  def getMovie(movieId: MovieId): IO[Option[Movie]]

  def saveMovie(movie: Movie): IO[MovieId]

}
