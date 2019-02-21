package com.reagroup.appliedscala.models

import io.circe.{Encoder, Json}
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, starRating: StarRating)

object EnrichedMovie {

  /**
    * Add an Encoder instance here
    */

}
