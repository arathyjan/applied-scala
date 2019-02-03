package com.reagroup.appliedscala.urls.savemovie

import cats.data._
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{MovieNameTooShort, SynopsisTooShort}
import com.reagroup.appliedscala.models.{MovieToSave, NewMovieRequest}
import org.scalatest.FunSpec

class NewMovieValidatorSpec extends FunSpec {

  describe("validate") {
    it("should return all errors if new movie has no name and no synopsis") {
      val newMovie = NewMovieRequest("", "")

      val result = NewMovieValidator.validate(newMovie)

      assert(result == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid)
    }

    it("should return NewMovie") {
      val newMovie = NewMovieRequest("badman returns", "nananana badman")

      val result = NewMovieValidator.validate(newMovie)

      assert(result == MovieToSave(newMovie.name, newMovie.synopsis).valid)
    }
  }

}
