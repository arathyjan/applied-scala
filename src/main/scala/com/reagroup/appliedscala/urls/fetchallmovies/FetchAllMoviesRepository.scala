package com.reagroup.appliedscala.urls.fetchallmovies

import cats.effect.IO
import com.reagroup.appliedscala.models._

trait FetchAllMoviesRepository {

  def apply(): IO[Vector[Movie]]

}
