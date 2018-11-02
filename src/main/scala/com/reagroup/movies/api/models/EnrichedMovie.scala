package com.reagroup.movies.api.models

import io.circe.{Encoder, Json}
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, optStarRating: Option[StarRating])

object EnrichedMovie {

  implicit val encoder: Encoder[EnrichedMovie] =
    Encoder.forProduct4("name", "synopsis", "reviews", "rating") {
      case EnrichedMovie(Movie(name, synopsis, reviews), optStarRating) =>
        (name, synopsis, reviews.asJson, optStarRating.map(StarRating.show).asJson)
    }

}
