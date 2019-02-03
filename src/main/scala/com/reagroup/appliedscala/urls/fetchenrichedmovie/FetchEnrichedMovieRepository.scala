package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._

trait FetchEnrichedMovieRepository {

  def fetchMovie(movieId: MovieId): IO[Option[Movie]]

}
