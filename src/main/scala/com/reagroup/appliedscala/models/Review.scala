package com.reagroup.appliedscala.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Review(author: String, comment: String)

object Review {

  implicit val encoder: Encoder[Review] = deriveEncoder[Review]

  implicit val decoder: Decoder[Review] = deriveDecoder[Review]

}
