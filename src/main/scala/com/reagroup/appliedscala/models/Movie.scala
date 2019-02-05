package com.reagroup.appliedscala.models

import io.circe.Encoder
import io.circe.generic.semiauto._

case class Movie(name: String, synopsis: String, reviews: Vector[Review])

object Movie {

  implicit val encoder: Encoder[Movie] = deriveEncoder[Movie]

}