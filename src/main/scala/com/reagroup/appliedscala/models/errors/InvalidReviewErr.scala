package com.reagroup.appliedscala.models.errors

import io.circe.Encoder

sealed trait InvalidReviewErr

case object AuthorTooShort extends InvalidReviewErr

case object CommentTooShort extends InvalidReviewErr

object InvalidReviewErr {

  implicit val encoder: Encoder[InvalidReviewErr] = ???

  def show(invalidReviewErr: InvalidReviewErr): String = ???

}
