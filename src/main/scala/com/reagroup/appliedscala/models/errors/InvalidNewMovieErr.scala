package com.reagroup.appliedscala.models.errors

import io.circe.Encoder

sealed trait InvalidNewMovieErr

case object MovieNameTooShort extends InvalidNewMovieErr

case object SynopsisTooShort extends InvalidNewMovieErr

object InvalidNewMovieErr {

  implicit val encoder: Encoder[InvalidNewMovieErr] =
    Encoder.forProduct1("error")(show)

  def show(err: InvalidNewMovieErr): String =
    err match {
      case MovieNameTooShort => "MOVIE_NAME_TOO_SHORT"
      case SynopsisTooShort => "SYNOPSIS_TOO_SHORT"
    }

}