package com.reagroup.movies.api.endpoints.movies
import cats.effect.IO
import com.reagroup.movies.api.models.{Movie, MovieId}

class InMemRepository extends MoviesRepository {

  override def getMovie(movieId: MovieId): IO[Option[Movie]] =
    IO.pure(Some(Movie("Batman", "Good movie", Vector.empty)))

  override def saveMovie(movie: Movie): IO[MovieId] =
    IO.pure(MovieId(12345))

}
