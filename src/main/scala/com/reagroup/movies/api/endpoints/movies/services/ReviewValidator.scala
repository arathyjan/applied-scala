package com.reagroup.movies.api.endpoints.movies.services

import cats.data.{Validated, ValidatedNel}
import com.reagroup.movies.api.models._
import cats.implicits._

object ReviewValidator {

  def validate(review: Review): ValidatedNel[InvalidReviewErr, Review] =
    (validateAuthor(review.author), validateComment(review.comment)).mapN(Review.apply)

  private def validateAuthor(author: String): ValidatedNel[InvalidReviewErr, String] =
    Validated.condNel(author.length > 0, author, AuthorTooShort)

  private def validateComment(comment: String): ValidatedNel[InvalidReviewErr, String] =
    Validated.condNel(comment.length > 5, comment, CommentTooShort)

}
