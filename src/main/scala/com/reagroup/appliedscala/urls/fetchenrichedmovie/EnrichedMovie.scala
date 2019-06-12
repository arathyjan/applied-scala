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
    *   "rating": "Five Stars"
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
    *   "starRating": "Five Stars"
    * }
    *
    * which is what we would get if we used `deriveEncoder[EnrichedMovie]`
    *
    * Hint: You will need to create a custom encoder. Also use `StarRating.show`
    */

  implicit val encoder: Encoder[EnrichedMovie] =
    Encoder.forProduct4("name", "synopsis", "reviews",
      "rating")(enrichedM =>
      (enrichedM.movie.name, enrichedM.movie.synopsis, enrichedM.movie.reviews, StarRating.show(enrichedM.starRating)))

}
