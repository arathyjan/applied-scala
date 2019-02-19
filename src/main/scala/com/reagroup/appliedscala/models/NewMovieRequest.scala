package com.reagroup.appliedscala.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder

case class NewMovieRequest(name: String, synopsis: String)

object NewMovieRequest {

  implicit val decoder: Decoder[NewMovieRequest] = ???

}