package com.reagroup.movies.api.endpoints.movies.services.interpreters

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.algebras.MoviesRepository
import com.reagroup.movies.api.endpoints.movies.services.algebras.MoviesServiceActions
import com.reagroup.movies.api.models.{Movie, MovieId, NewMovie}

class MoviesService(repository: MoviesRepository) extends MoviesServiceActions {

  def get(movieId: MovieId): IO[Option[Movie]] = {
    repository.getMovie(movieId)
  }

  // TODO: Add validation to NewMovie?
  // Show that this is easy to test
  def save(newMovieReq: NewMovie): IO[MovieId] = {
    val movie = Movie(newMovieReq.name, newMovieReq.synopsis, Vector.empty)
    repository.saveMovie(movie)
  }

}