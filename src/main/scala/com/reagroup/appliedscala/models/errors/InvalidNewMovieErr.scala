package com.reagroup.appliedscala.models.errors

import io.circe.Encoder

sealed trait InvalidNewMovieErr

case object MovieNameTooShort extends InvalidNewMovieErr

case object MovieSynopsisTooShort extends InvalidNewMovieErr

object InvalidNewMovieErr {

  /**
    * Write a function that turns an `InvalidNewMovieErr` to a `String`.
    * This will be used in our `Encoder`.
    *
    * Hint: Use pattern matching
    */
  def show(err: InvalidNewMovieErr): String = ???

  /**
    * Add an Encoder instance here
    */

}