package com.reagroup.movies.api.endpoints.movies.repositories.interpreters

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.MoviesRepository
import com.reagroup.movies.api.models.{Movie, MovieId, NewMovie}

class InMemRepository extends MoviesRepository {

  override def getMovie(movieId: MovieId): IO[Option[Movie]] =
    IO.raiseError(new RuntimeException("JACK IS DONE"))

  override def saveMovie(movie: NewMovie): IO[MovieId] =
    IO.pure(MovieId(12345))

}
