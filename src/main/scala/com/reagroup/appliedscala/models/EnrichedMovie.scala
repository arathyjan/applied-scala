package com.reagroup.appliedscala.models

import io.circe.{Encoder, Json}
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, starRating: StarRating)

object EnrichedMovie {

  /**
    * Add an Encoder instance here
    *
    * We want the Json to look like:
    *
    * {
    *   "name": "Batman",
    *   "synopsis": "Great movie for the family",
    *   "reviews": []
    *   "rating": "Five Star"
    * }
    *
    * Hint: You will need to create a custom encoder.
    */

  implicit val encoder: Encoder[EnrichedMovie] =
    (a: EnrichedMovie) => Json.obj(
      "name" -> a.movie.name.asJson,
      "synopsis" -> a.movie.synopsis.asJson,
      "reviews" -> a.movie.reviews.asJson,
      "rating" -> a.starRating.asJson
    )
}
