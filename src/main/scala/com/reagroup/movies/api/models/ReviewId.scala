package com.reagroup.movies.api.models

import io.circe.Encoder

case class ReviewId(value: Long)

object ReviewId {

  implicit val encoder: Encoder[ReviewId] = Encoder.forProduct1("reviewId")(_.value)

}
