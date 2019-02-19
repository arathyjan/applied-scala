package com.reagroup.appliedscala.urls.savemovie

import cats.data._
import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.InvalidNewMovieErr

class SaveMovieService(saveMovie: MovieToSave => IO[MovieId]) {

  def save(newMovieReq: NewMovieRequest): IO[ValidatedNel[InvalidNewMovieErr, MovieId]] = ???

}
