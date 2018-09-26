package com.reagroup.movies.api.models

import io.circe.Encoder
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, starRating: StarRating)

object EnrichedMovie {

  implicit val encoder: Encoder[EnrichedMovie] =
    Encoder.forProduct4("name", "synopsis", "reviews", "rating") {
      case EnrichedMovie(Movie(name, synopsis, reviews), starRating) =>
        (name, synopsis, reviews.asJson, StarRating.show(starRating))
    }

}
