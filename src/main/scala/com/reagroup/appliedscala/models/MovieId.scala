package com.reagroup.appliedscala.models

import io.circe.Encoder
import io.circe.Json
import io.circe.generic.semiauto.deriveEncoder

case class MovieId(value: Long)

object MovieId {


  /**
    * Add an Encoder instance here
    *
    * We want the resulting Json to look like this:
    *
    * {
    *   "id": 1
    * }
    *
    * Hint: You don't want to use `deriveEncoder` here
    */

  implicit val encoder: Encoder[MovieId] = Encoder.forProduct1("id")(movieId => movieId.value)
}
