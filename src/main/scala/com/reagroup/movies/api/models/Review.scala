package com.reagroup.movies.api.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class Review(author: String, comment: String)

object Review {

  implicit val encoder: Encoder[Review] = deriveEncoder[Review]

}
