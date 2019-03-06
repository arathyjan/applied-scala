package com.reagroup.appliedscala.urls.savereview

sealed trait ReviewValidationError

case object ReviewAuthorTooShort extends ReviewValidationError

case object ReviewCommentTooShort extends ReviewValidationError

object ReviewValidationError {

  /**
    * Write a function that turns an `ReviewValidationError` to a `String`.
    * This will be used in our `Encoder`.
    *
    * `ReviewAuthorTooShort` -> "REVIEW_AUTHOR_TOO_SHORT"
    * `ReviewCommentTooShort` -> "REVIEW_COMMENT_TOO_SHORT"
    *
    * Hint: Use pattern matching
    */
  def show(invalidReviewErr: ReviewValidationError): String = ???

  /**
    * Add an Encoder instance here
    */

}
