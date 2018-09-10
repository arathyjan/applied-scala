package com.reagroup.movies.api.endpoints.movies.services.effects

import cats.effect.IO
import com.reagroup.movies.api.models.{Movie, MovieId, NewMovie}

trait MoviesServiceEffects {

  def get(movieId: MovieId): IO[Option[Movie]]

  def save(newMovieReq: NewMovie): IO[MovieId]

}
