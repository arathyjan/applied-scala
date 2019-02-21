package com.reagroup.appliedscala.urls.savereview

import cats.data.{Validated, ValidatedNel}
import cats.implicits._
import com.reagroup.appliedscala.models.errors.{AuthorTooShort, CommentTooShort, InvalidReviewErr}
import com.reagroup.appliedscala.models.{NewReviewRequest, ReviewToSave}

object ReviewValidator {

  /**
    * How do we combine two validations together? Refer to `ValidationExercises` for a refresher!
    *
    * Hint: `Validated` has an Applicativec instance.
    */
  def validate(review: NewReviewRequest): ValidatedNel[InvalidReviewErr, ReviewToSave] = ???

  private def validateAuthor(author: String): ValidatedNel[InvalidReviewErr, String] = ???

  private def validateComment(comment: String): ValidatedNel[InvalidReviewErr, String] = ???

}
