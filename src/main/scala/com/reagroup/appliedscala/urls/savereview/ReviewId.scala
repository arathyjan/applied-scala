package com.reagroup.appliedscala.urls.savereview

import io.circe.Encoder

case class ReviewId(value: Long)

object ReviewId {

  /**
    * Add an Encoder instance here
    *
    * We want the resulting Json to look like this:
    *
    * {
    *   "id": 1
    * }
    */
  implicit val encoder: Encoder[ReviewId] = Encoder.forProduct1("id")(_.value)

}
