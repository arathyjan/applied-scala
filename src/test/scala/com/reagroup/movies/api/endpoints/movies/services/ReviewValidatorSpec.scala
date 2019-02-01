package com.reagroup.movies.api.endpoints.movies.services

import cats.data._
import cats.implicits._
import com.reagroup.movies.api.models.{Review, ReviewToSave}
import org.scalatest.FunSpec

class ReviewValidatorSpec extends FunSpec {

  describe("validate") {
    it("should return all errors if new review has no name and no synopsis") {
      val review = Review("", "")

      val result = ReviewValidator.validate(review)

      assert(result == NonEmptyList.of(AuthorTooShort, CommentTooShort).invalid)
    }

    it("should return NewMovie") {
      val review = Review("bob", "cool movie")

      val result = ReviewValidator.validate(review)

      assert(result == ReviewToSave("bob", "cool movie").valid)
    }
  }

}
