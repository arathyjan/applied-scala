package com.reagroup.appliedscala.urls.savemovie

import com.reagroup.appliedscala.urls.savereview.NewReviewRequest
import io.circe.Decoder
import io.circe.generic.semiauto._

case class NewMovieRequest(name: String, synopsis: String)

object NewMovieRequest {

  /**
    * Add a Decoder instance here to decode the following Json:
    *
    * {
    *   "name": "Titanic",
    *   "synopsis": "A movie about ships"
    * }
    *
    * Hint: The keys in the JSON are named exactly the same as the fields in `NewMovieRequest`.
    */

  implicit val newMovieDecoder = deriveDecoder[NewMovieRequest]


}