package com.reagroup.appliedscala.models.errors

import io.circe.Encoder

sealed trait InvalidNewMovieErr

case object MovieNameTooShort extends InvalidNewMovieErr

case object SynopsisTooShort extends InvalidNewMovieErr

object InvalidNewMovieErr {

  implicit val encoder: Encoder[InvalidNewMovieErr] = ???

  def show(err: InvalidNewMovieErr): String = ???

}