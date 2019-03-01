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

  // TODO unit test
  def fromScore(score: Int): Option[StarRating] =
    if (score >= 0 && score < 20) Some(One)
    else if (score >= 20 && score < 40) Some(Two)
    else if (score >= 40 && score < 60) Some(Three)
    else if (score >= 60 && score < 80) Some(Four)
    else if (score >= 80 && score <= 100) Some(Five)
    else None

  // TODO unit test
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
    *
    * Hint: Use `.toRight(DecodingFailure("Score is out of range: 0-100", c.history))` to convert an `Option` to an `Either`
    */

  implicit val decoder: Decoder[StarRating] = (c: HCursor) => {
    c.get[Int]("Metascore").flatMap(score => fromScore(score).toRight(DecodingFailure("Score is out of range: 0-100", c.history)))
  }

}