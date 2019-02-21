package com.reagroup.appliedscala.urls.savemovie

import cats.data.{Validated, ValidatedNel}
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{InvalidNewMovieErr, MovieNameTooShort, SynopsisTooShort}
import com.reagroup.appliedscala.models.{MovieToSave, NewMovieRequest}

object NewMovieValidator {

  /**
    * How do we combine two validations together? Refer to `ValidationExercises` for a refresher!
    *
    * Hint: `Validated` has an Applicativec instance.
    */
  def validate(newMovie: NewMovieRequest): ValidatedNel[InvalidNewMovieErr, MovieToSave] = ???

  private def validateMovieName(name: String): ValidatedNel[InvalidNewMovieErr, String] = ???

  private def validateMovieSynopsis(synopsis: String): ValidatedNel[InvalidNewMovieErr, String] = ???

}
