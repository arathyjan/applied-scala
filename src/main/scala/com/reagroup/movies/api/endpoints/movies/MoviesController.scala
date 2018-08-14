package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.models._
import org.http4s._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._

class MoviesController(f: MovieId => IO[Option[Movie]], g: Movie => IO[MovieId]) {

  def getMovie(movieId: Long): IO[Response[IO]] =
    f(MovieId(movieId)).flatMap(encodeOptMovie)

  def saveMovie(req: Request[IO]): IO[Response[IO]] =
    req.as[NewMovieRequest].flatMap {
      case NewMovieRequest(name, synopsis) =>
        val movie = Movie(name, synopsis, Vector.empty)
        g(movie).flatMap(encodeMovieId)
    }

  private def encodeOptMovie(optMovie: Option[Movie]): IO[Response[IO]] = ???

  private def encodeMovieId(movieId: MovieId): IO[Response[IO]] = ???

}
