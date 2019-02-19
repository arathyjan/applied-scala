package com.reagroup.appliedscala.urls.savemovie

import cats.data._
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{MovieNameTooShort, SynopsisTooShort}
import com.reagroup.appliedscala.models.{MovieToSave, NewMovieRequest}
import org.specs2.mutable.Specification

class NewMovieValidatorSpec extends Specification {

  "validate" should {
    "return all errors if new movie has no name and no synopsis" in {
      val newMovie = NewMovieRequest("", "")

      val result = NewMovieValidator.validate(newMovie)

      result == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid
    }

    "return NewMovie" in {
      val newMovie = NewMovieRequest("badman returns", "nananana badman")

      val result = NewMovieValidator.validate(newMovie)

      result == MovieToSave(newMovie.name, newMovie.synopsis).valid
    }
  }

}
