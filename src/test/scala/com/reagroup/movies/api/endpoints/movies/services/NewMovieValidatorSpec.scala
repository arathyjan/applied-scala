package com.reagroup.movies.api.endpoints.movies.services

import cats.data._
import cats.implicits._
import com.reagroup.movies.api.models.{MovieToSave, NewMovie}
import org.scalatest.FunSpec

class NewMovieValidatorSpec extends FunSpec {

  describe("validate") {
    it("should return all errors if new movie has no name and no synopsis") {
      val newMovie = NewMovie("", "")

      val result = NewMovieValidator.validate(newMovie)

      assert(result == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid)
    }

    it("should return NewMovie") {
      val newMovie = NewMovie("badman returns", "nananana badman")

      val result = NewMovieValidator.validate(newMovie)

      assert(result == MovieToSave(newMovie.name, newMovie.synopsis).valid)
    }
  }

}
