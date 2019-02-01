package com.reagroup.appliedscala.urls.savereview

import cats.data.{Validated, ValidatedNel}
import cats.implicits._
import com.reagroup.appliedscala.models.{Review, ReviewToSave}

object ReviewValidator {

  def validate(review: Review): ValidatedNel[InvalidReviewErr, ReviewToSave] =
    (validateAuthor(review.author), validateComment(review.comment)).mapN(ReviewToSave.apply)

  private def validateAuthor(author: String): ValidatedNel[InvalidReviewErr, String] =
    Validated.condNel(author.length > 0, author, AuthorTooShort)

  private def validateComment(comment: String): ValidatedNel[InvalidReviewErr, String] =
    Validated.condNel(comment.length > 5, comment, CommentTooShort)

}
