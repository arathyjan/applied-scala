package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._

trait FetchMovieRepository {

  def apply(movieId: MovieId): IO[Option[Movie]]

}
