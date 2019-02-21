package com.reagroup.appliedscala.models

import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

import scala.util.Try
import io.circe.syntax._

sealed trait StarRating

case object Five extends StarRating

case object Four extends StarRating

case object Three extends StarRating

case object Two extends StarRating

case object One extends StarRating

object StarRating {

  def show(starRating: StarRating): String = ???

  /**
    * Add an Encoder instance here
    */

  /**
    * Add a Decoder instance here
    */
}