package com.reagroup.appliedscala.urls.savereview

sealed trait InvalidNewReviewErr

case object ReviewAuthorTooShort extends InvalidNewReviewErr

case object ReviewCommentTooShort extends InvalidNewReviewErr

object InvalidNewReviewErr {

  /**
    * Write a function that turns an `InvalidNewReviewErr` to a `String`.
    * This will be used in our `Encoder`.
    *
    * Hint: Use pattern matching
    */
  def show(invalidReviewErr: InvalidNewReviewErr): String = ???

  /**
    * Add an Encoder instance here
    */

}
