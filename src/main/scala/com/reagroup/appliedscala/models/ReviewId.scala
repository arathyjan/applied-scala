package com.reagroup.appliedscala.models

import io.circe.Encoder

case class ReviewId(value: Long)

object ReviewId {

  implicit val encoder: Encoder[ReviewId] = ???

}
