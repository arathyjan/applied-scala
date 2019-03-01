package com.reagroup.appliedscala.urls.savereview

import cats.data.{Validated, ValidatedNel}
import cats.implicits._

object ReviewValidator {

  /**
    * How do we combine two validations together? Refer to `ValidationExercises` for a refresher!
    *
    * Hint: `Validated` has an Applicative instance.
    */
  def validate(review: NewReviewRequest): ValidatedNel[InvalidNewReviewErr, ValidatedReview] = ???

  private def validateAuthor(author: String): ValidatedNel[InvalidNewReviewErr, String] = ???

  private def validateComment(comment: String): ValidatedNel[InvalidNewReviewErr, String] = ???

}
