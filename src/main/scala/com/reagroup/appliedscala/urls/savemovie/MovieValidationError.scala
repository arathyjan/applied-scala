package com.reagroup.appliedscala.urls.savemovie

import io.circe.Encoder

sealed trait MovieValidationError

case object MovieNameTooShort extends MovieValidationError

case object MovieSynopsisTooShort extends MovieValidationError

object MovieValidationError {

  /**
    * Write a function that turns an `MovieValidationError` to a `String`.
    * This will be used in our `Encoder`.
    *
    * Hint: Use pattern matching
    */
  def show(err: MovieValidationError): String = ???

  /**
    * Add an Encoder instance here
    */

}