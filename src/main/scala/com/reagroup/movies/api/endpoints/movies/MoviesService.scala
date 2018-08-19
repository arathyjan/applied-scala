package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.models.{Movie, MovieId, NewMovie}

class MoviesService(repository: MoviesRepository) {

  def get(movieId: MovieId): IO[Option[Movie]] = {
    repository.getMovie(movieId)
  }

  def save(newMovieReq: NewMovie): IO[MovieId] = {
    val movie = Movie(newMovieReq.name, newMovieReq.synopsis, Vector.empty)
    repository.saveMovie(movie)
  }

}
