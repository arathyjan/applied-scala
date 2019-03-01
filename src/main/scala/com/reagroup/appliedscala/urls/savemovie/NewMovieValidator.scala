package com.reagroup.appliedscala.urls.savemovie

import cats.data.Validated._
import cats.data.{NonEmptyList, Validated, ValidatedNel}
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{InvalidNewMovieErr, MovieNameTooShort, MovieSynopsisTooShort}

object NewMovieValidator {

  /**
    * How do we combine two validations together? Refer to `ValidationExercises` for a refresher!
    *
    * Hint: `Validated` has an Applicative instance.
    */
  def validate(newMovie: NewMovieRequest): ValidatedNel[InvalidNewMovieErr, ValidatedMovie] = {
    val name = newMovie.name
    val synopsis = newMovie.synopsis

    val validatedName: ValidatedNel[InvalidNewMovieErr, String] = validateMovieName(name)
    val validatedSynopsis: ValidatedNel[InvalidNewMovieErr, String] = validateMovieSynopsis(synopsis)

    (validatedName, validatedSynopsis).mapN(ValidatedMovie.apply)
  }

  private def validateMovieName(name: String): ValidatedNel[InvalidNewMovieErr, String] =
    if (name.nonEmpty) name.valid
    else MovieNameTooShort.invalidNel

  private def validateMovieSynopsis(synopsis: String): ValidatedNel[InvalidNewMovieErr, String] =
    if (synopsis.nonEmpty) synopsis.valid
    else MovieSynopsisTooShort.invalidNel

}
