package com.reagroup.appliedscala.models

import io.circe.{Encoder, Json}
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, starRating: StarRating)

object EnrichedMovie {

  /**
    * Add an Encoder instance here
    *
    * // TODO specify how the Json needs to look
    */

  implicit val encoder: Encoder[EnrichedMovie] =
    (a: EnrichedMovie) => Json.obj(
      "name" -> a.movie.name.asJson,
      "synopsis" -> a.movie.synopsis.asJson,
      "reviews" -> a.movie.reviews.asJson,
      "rating" -> a.starRating.asJson
    )
}
