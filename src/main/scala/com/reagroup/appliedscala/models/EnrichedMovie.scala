package com.reagroup.appliedscala.models

import io.circe.{Encoder, Json}
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, starRating: StarRating)

object EnrichedMovie {

  implicit val encoder: Encoder[EnrichedMovie] = {
    case EnrichedMovie(Movie(name, synopsis, reviews), starRating) =>
      Json.obj(
        "name" -> name.asJson,
        "synopsis" -> synopsis.asJson,
        "reviews" -> reviews.asJson,
        "rating" -> starRating.asJson
      )
  }

}
