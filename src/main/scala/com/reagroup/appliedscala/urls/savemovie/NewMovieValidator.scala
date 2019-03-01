package com.reagroup.appliedscala.urls.savemovie

import cats.data.Validated._
import cats.data.{NonEmptyList, Validated, ValidatedNel}
import cats.implicits._

object NewMovieValidator {

  /**
    * How do we combine two validations together? Refer to `ValidationExercises` for a refresher!
    *
    * Hint: `Validated` has an Applicative instance.
    */
  def validate(newMovie: NewMovieRequest): ValidatedNel[MovieValidationError, ValidatedMovie] = {
    val name = newMovie.name
    val synopsis = newMovie.synopsis

    val validatedName: ValidatedNel[MovieValidationError, String] = validateMovieName(name)
    val validatedSynopsis: ValidatedNel[MovieValidationError, String] = validateMovieSynopsis(synopsis)

    (validatedName, validatedSynopsis).mapN(ValidatedMovie.apply)
  }

  private def validateMovieName(name: String): ValidatedNel[MovieValidationError, String] =
    if (name.nonEmpty) name.valid
    else MovieNameTooShort.invalidNel

  private def validateMovieSynopsis(synopsis: String): ValidatedNel[MovieValidationError, String] =
    if (synopsis.nonEmpty) synopsis.valid
    else MovieSynopsisTooShort.invalidNel

}
