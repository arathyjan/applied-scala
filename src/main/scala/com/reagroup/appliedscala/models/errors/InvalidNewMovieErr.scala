package com.reagroup.appliedscala.models.errors

import io.circe.Encoder

sealed trait InvalidNewMovieErr

case object MovieNameTooShort extends InvalidNewMovieErr

case object SynopsisTooShort extends InvalidNewMovieErr

object InvalidNewMovieErr {

  /**
    * Add an Encoder instance here
    */

  def show(err: InvalidNewMovieErr): String = ???

}