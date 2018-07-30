package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.models._
import AppResponse.toAppResp

class MoviesController(repository: MoviesRepository) {

  def apply(request: AppRequest): IO[AppResponse] =
    request match {
      case GetMovieReq(id) =>
        val optMovie = repository.getMovie(id)
        toAppResp(optMovie, MovieResp)

      case PostMovieReq(name, synopsis) =>
        val newMovie = Movie(name, synopsis, Vector.empty)
        val ioMovieId = repository.saveMovie(newMovie)
        toAppResp(ioMovieId, NewMovieResp)

      case InvalidReq(_, error) => IO.pure(ErrorResp(InvalidRequestErr, error))
    }

}
