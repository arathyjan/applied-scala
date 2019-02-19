package com.reagroup.appliedscala.models.errors

import io.circe.Encoder

sealed trait InvalidReviewErr

case object AuthorTooShort extends InvalidReviewErr

case object CommentTooShort extends InvalidReviewErr

object InvalidReviewErr {

  implicit val encoder: Encoder[InvalidReviewErr] = Encoder.forProduct1("type")(show)

  def show(invalidReviewErr: InvalidReviewErr): String =
    invalidReviewErr match {
      case AuthorTooShort => "AUTHOR_TOO_SHORT"
      case CommentTooShort => "COMMENT_TOO_SHORT"
    }

}
