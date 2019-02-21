package com.reagroup.appliedscala.models

import io.circe.Decoder
import io.circe.generic.semiauto._

case class NewReviewRequest(author: String, comment: String)

object NewReviewRequest {

  /**
    * Add an Decoder instance here
    */

}