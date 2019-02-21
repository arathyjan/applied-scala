package com.reagroup.appliedscala.urls.savereview

import cats.data._
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{AuthorTooShort, CommentTooShort}
import com.reagroup.appliedscala.models.{NewReviewRequest, ReviewToSave}
import org.specs2.mutable.Specification

class ReviewValidatorSpec extends Specification {

  "validate" should {
    "return all errors if new review has no name and no synopsis" in {
      val review = NewReviewRequest("", "")

      val result = ReviewValidator.validate(review)

      result must_=== NonEmptyList.of(AuthorTooShort, CommentTooShort).invalid
    }

    "return NewMovie" in {
      val review = NewReviewRequest("bob", "cool movie")

      val result = ReviewValidator.validate(review)

      result must_=== ReviewToSave("bob", "cool movie").valid
    }
  }

}
