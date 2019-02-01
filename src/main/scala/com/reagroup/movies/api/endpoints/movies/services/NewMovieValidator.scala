package com.reagroup.movies.api.endpoints.movies.services

import cats.implicits._
import cats.data.{Validated, ValidatedNel}
import com.reagroup.movies.api.models.{MovieToSave, NewMovie}

object NewMovieValidator {

  def validate(newMovie: NewMovie): ValidatedNel[InvalidNewMovieErr, MovieToSave] = {
    val validateName = validateMovieName(newMovie.name)
    val validateSynopsis = validateMovieSynopsis(newMovie.synopsis)
    (validateName, validateSynopsis).mapN(MovieToSave(_, _))
  }

  private def validateMovieName(name: String): ValidatedNel[InvalidNewMovieErr, String] =
    Validated.condNel(name.length > 0, name, MovieNameTooShort)

  private def validateMovieSynopsis(synopsis: String): ValidatedNel[InvalidNewMovieErr, String] =
    Validated.condNel(synopsis.length > 10, synopsis, SynopsisTooShort)

}
