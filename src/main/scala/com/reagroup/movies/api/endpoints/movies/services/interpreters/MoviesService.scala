package com.reagroup.movies.api.endpoints.movies.services.interpreters

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.MoviesRepository
import com.reagroup.movies.api.endpoints.movies.services.effects.MoviesServiceEffects
import com.reagroup.movies.api.models.{Movie, MovieId, NewMovie}

class MoviesService(repository: MoviesRepository) extends MoviesServiceEffects {

  def get(movieId: MovieId): IO[Option[Movie]] = {
    repository.getMovie(movieId)
  }

  // TODO: Add validation to NewMovie?
  // Show that this is easy to test
  def save(movie: NewMovie): IO[MovieId] = {
    repository.saveMovie(movie)
  }

}