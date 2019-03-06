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
    *
    * `MovieNameTooShort` -> "MOVIE_NAME_TOO_SHORT"
    * `MovieSynopsisTooShort` -> "MOVIE_SYNOPSIS_TOO_SHORT"
    *
    * Hint: Use pattern matching
    */
  def show(err: MovieValidationError): String =
    err match {
      case MovieNameTooShort => "MOVIE_NAME_TOO_SHORT"
      case MovieSynopsisTooShort => "MOVIE_SYNOPSIS_TOO_SHORT"
    }

  /**
    * Add an Encoder instance here
    *
    * We want the resulting Json to look like this:
    *
    * {
    *   "type": "MOVIE_NAME_TOO_SHORT"
    * }
    *
    * Hint: You don't want to use `deriveEncoder` here
    */

  implicit val encoder: Encoder[MovieValidationError] = Encoder.forProduct1("type")(show)

}