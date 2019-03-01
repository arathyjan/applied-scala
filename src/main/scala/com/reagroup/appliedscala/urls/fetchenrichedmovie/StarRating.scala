package com.reagroup.appliedscala.urls.fetchenrichedmovie

import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

sealed trait StarRating

case object Four extends StarRating

case object Three extends StarRating

case object Two extends StarRating

case object One extends StarRating

object StarRating {

  /**
    * Write a function that turns a score to an `Option[StarRating]`
    *
    * If the score is
    * >=0 and <25, return `One`
    * >=25 and <50, return `Two`
    * >=50 and <75, return `Three`
    * >=75 and <=100, return `Four`
    *
    * If the score is out of range, return `None`
    */
  def fromScore(score: Int): Option[StarRating] =
    if (score >= 0 && score < 25) Some(One)
    else if (score >= 25 && score < 50) Some(Two)
    else if (score >= 50 && score < 75) Some(Three)
    else if (score >= 75 && score <= 100) Some(Four)
    else None

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
  }

  /**
    * Add an Encoder instance here
    *
    * Hint: Use `show`
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