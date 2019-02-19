package com.reagroup.appliedscala.models

import io.circe.Encoder

case class MovieId(value: Long)

object MovieId {

  implicit val encoder: Encoder[MovieId] = ???

}
