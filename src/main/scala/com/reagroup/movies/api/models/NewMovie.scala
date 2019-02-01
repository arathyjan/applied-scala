package com.reagroup.movies.api.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder

case class NewMovie(name: String, synopsis: String)

object NewMovie {

  implicit val encoder: Encoder[NewMovie] = deriveEncoder[NewMovie]

  implicit val decoder: Decoder[NewMovie] = deriveDecoder[NewMovie]

}