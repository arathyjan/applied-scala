package com.reagroup.appliedscala.urls.fetchallmovies

import cats.effect.IO
import com.reagroup.appliedscala.models._

class FetchAllMoviesService(repo: FetchAllMoviesRepository) {

  def fetchAllMovies(): IO[Vector[Movie]] =
    repo()

}
