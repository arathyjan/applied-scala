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
    ???

  /**
    * Write a function that turns a `StarRating` to a `String`.
    * We will use this in our `Encoder`.
    *
    * One -> "One Star"
    * Two -> "Two Stars"
    * Three -> "Three Stars"
    * etc.
    */
  def show(starRating: StarRating): String =
    ???

  /**
    * Add a Decoder instance here
    *
    * Hint: Use `.toRight(DecodingFailure("Score is out of range: 0-100", c.history))` to convert an `Option` to an `Either`
    */

}