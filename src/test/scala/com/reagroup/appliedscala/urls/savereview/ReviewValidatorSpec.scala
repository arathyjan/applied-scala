package com.reagroup.appliedscala.urls.savereview

import cats.data._
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{AuthorTooShort, CommentTooShort}
import com.reagroup.appliedscala.models.{NewReviewRequest, ReviewToSave}
import org.scalatest.FunSpec

class ReviewValidatorSpec extends FunSpec {

  describe("validate") {
    it("should return all errors if new review has no name and no synopsis") {
      val review = NewReviewRequest("", "")

      val result = ReviewValidator.validate(review)

      assert(result == NonEmptyList.of(AuthorTooShort, CommentTooShort).invalid)
    }

    it("should return NewMovie") {
      val review = NewReviewRequest("bob", "cool movie")

      val result = ReviewValidator.validate(review)

      assert(result == ReviewToSave("bob", "cool movie").valid)
    }
  }

}
