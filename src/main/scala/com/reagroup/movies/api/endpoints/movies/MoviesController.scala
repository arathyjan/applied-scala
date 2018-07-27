package com.reagroup.movies.api.endpoints.movies

import cats.effect.IO
import com.reagroup.movies.api.models._

class MoviesController(repository: MoviesRepository) {

  def apply(request: AppRequest): IO[AppResponse] =
    request match {
      case GetMovieReq(id) => AppResponse.fromIO(repository.getMovie(id), GetMovieResp)
      case PostMovieReq(name, synopsis) =>
        val ioMovieId = repository.saveMovie(Movie(name, synopsis, Vector.empty))
        AppResponse.fromIO(ioMovieId, PostMovieResp)
      case InvalidReq(_, error) => IO.pure(ErrorResp(InvalidRequestErr, error))
      case UnknownReq(req) => IO.pure(ErrorResp(RouteNotFoundErr, req.toString()))
    }

}
