package com.reagroup.movies.api.models

import io.circe.{Encoder, Json}
import io.circe.syntax._

case class EnrichedMovie(movie: Movie, optStarRating: Option[StarRating])

object EnrichedMovie {

  implicit val encoder: Encoder[EnrichedMovie] = {
    case EnrichedMovie(Movie(name, synopsis, reviews), Some(starRating)) =>
      Json.obj(
        "name" -> name.asJson,
        "synopsis" -> synopsis.asJson,
        "reviews" -> reviews.asJson,
        "rating" -> starRating.asJson
      )
    case EnrichedMovie(Movie(name, synopsis, reviews), None) =>
      Json.obj(
        "name" -> name.asJson,
        "synopsis" -> synopsis.asJson,
        "reviews" -> reviews.asJson
      )
  }

}
