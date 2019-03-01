package com.reagroup.appliedscala.urls.fetchenrichedmovie

import com.reagroup.appliedscala.models.Movie
import io.circe.syntax._
import io.circe.{Encoder, Json}

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
    * not:
    *
    * {
    *   "movie": {
    *     "name": "Batman",
    *     "synopsis": "Great movie for the family",
    *     "reviews": []
    *   },
    *   "starRating": "Five Star"
    * }
    *
    * which is what we would get if we used `deriveEncoder[EnrichedMovie]`
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
