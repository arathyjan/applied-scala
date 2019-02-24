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

  def show(starRating: StarRating): String = starRating match {
    case One => "One Star"
    case Two => "Two Star"
    case Three => "Three Star"
    case Four => "Four Star"
    case Five => "Five Star"
  }

  /**
    * Add an Encoder instance here
    */

  implicit val encoder: Encoder[StarRating] = Encoder.forProduct1("rating")(StarRating.show)

  /**
    * Add a Decoder instance here
    */

  implicit val decoder: Decoder[StarRating] = (c: HCursor) => {
    c.get[Int]("Metascore").map(num => {
      if (num >= 0 && num < 20) One
      else if (num >= 20 && num < 40) Two
      else if (num >= 40 && num < 60) Three
      else if (num >= 60 && num < 80) Four
      else Five
    })
  }

}