package com.reagroup.appliedscala.urls.savereview

import cats.data.Validated._
import cats.data.{Validated, ValidatedNel}
import cats.implicits._

object NewReviewValidator {

  /**
    * How do we combine two validations together? Refer to `ValidationExercises` for a refresher!
    *
    * Hint: `Validated` has an Applicative instance.
    */
  def validate(review: NewReviewRequest): ValidatedNel[ReviewValidationError, ValidatedReview] = ???

  private def validateAuthor(author: String): ValidatedNel[ReviewValidationError, String] = ???

  private def validateComment(comment: String): ValidatedNel[ReviewValidationError, String] = ???

}
