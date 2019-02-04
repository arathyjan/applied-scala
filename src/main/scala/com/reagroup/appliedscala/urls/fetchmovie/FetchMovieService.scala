package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._

class FetchMovieService(repo: FetchMovieRepository) {

  def fetchMovie(movieId: MovieId): IO[Option[Movie]] =
    repo(movieId)

}
