package com.reagroup.appliedscala.urls.savemovie

import cats.data.{Validated, ValidatedNel}
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{InvalidNewMovieErr, MovieNameTooShort, SynopsisTooShort}
import com.reagroup.appliedscala.models.{MovieToSave, NewMovieRequest}

object NewMovieValidator {

  def validate(newMovie: NewMovieRequest): ValidatedNel[InvalidNewMovieErr, MovieToSave] = {
    val validateName = validateMovieName(newMovie.name)
    val validateSynopsis = validateMovieSynopsis(newMovie.synopsis)
    (validateName, validateSynopsis).mapN(MovieToSave(_, _))
  }

  private def validateMovieName(name: String): ValidatedNel[InvalidNewMovieErr, String] =
    Validated.condNel(name.length > 0, name, MovieNameTooShort)

  private def validateMovieSynopsis(synopsis: String): ValidatedNel[InvalidNewMovieErr, String] =
    Validated.condNel(synopsis.length > 10, synopsis, SynopsisTooShort)

}
