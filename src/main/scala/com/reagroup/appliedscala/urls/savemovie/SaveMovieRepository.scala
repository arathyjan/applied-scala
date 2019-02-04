package com.reagroup.appliedscala.urls.savemovie

import cats.effect.IO
import com.reagroup.appliedscala.models._

trait SaveMovieRepository {

  def apply(movie: MovieToSave): IO[MovieId]

}
