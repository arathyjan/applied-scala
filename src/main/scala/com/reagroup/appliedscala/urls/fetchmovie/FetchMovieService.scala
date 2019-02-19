package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._

class FetchMovieService(fetchMovie: MovieId => IO[Option[Movie]]) {

  def fetch(movieId: MovieId): IO[Option[Movie]] = ???

}
