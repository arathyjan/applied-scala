package com.reagroup.appliedscala.urls.fetchenrichedmovie

import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

sealed trait StarRating

case object Five extends StarRating

case object Four extends StarRating

case object Three extends StarRating

case object Two extends StarRating

case object One extends StarRating

object StarRating {

  // TODO unit test
  /**
    * Write a function that turns a score to an `Option[StarRating]`
    *
    * If the score is
    * >=0 and <20, return `One`
    * >=20 and <40, return `Two`
    * >=40 and <60, return `Three`
    * >=60 and <80, return `Four`
    * >=80 and <=100, return `Five`
    *
    * If the score is out of range, return `None`
    */
  def fromScore(score: Int): Option[StarRating] =
    if (score >= 0 && score < 20) Some(One)
    else if (score >= 20 && score < 40) Some(Two)
    else if (score >= 40 && score < 60) Some(Three)
    else if (score >= 60 && score < 80) Some(Four)
    else if (score >= 80 && score <= 100) Some(Five)
    else None

  // TODO unit test
  /**
    * Write a function that turns a `StarRating` to a `String`.
    * We will use this in our `Encoder`.
    *
    * One -> "One Star"
    * Two -> "Two Stars"
    * Three -> "Three Stars"
    * etc.
    */
  def show(starRating: StarRating): String = starRating match {
    case One => "One Star"
    case Two => "Two Stars"
    case Three => "Three Stars"
    case Four => "Four Stars"
    case Five => "Five Stars"
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