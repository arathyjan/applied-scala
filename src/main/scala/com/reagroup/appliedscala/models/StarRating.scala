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

  def show(starRating: StarRating): String =
    starRating match {
      case Five => "⭐️⭐️⭐️⭐️⭐️"
      case Four => "⭐️⭐️⭐️⭐️"
      case Three => "⭐️⭐️⭐️"
      case Two => "⭐️⭐️"
      case One => "⭐️"
    }

  implicit val encoder: Encoder[StarRating] = (starRating: StarRating) => show(starRating).asJson

  implicit val decoder: Decoder[StarRating] =
    (c: HCursor) => c.get[String]("Metascore").flatMap(str => Try(str.toDouble).toOption match {
      case Some(d) =>
        if (d >= 0 && d < 20)
          Right(One)
        else if (d >= 20 && d < 40)
          Right(Two)
        else if (d >= 40 && d < 60)
          Right(Three)
        else if (d >= 60 && d < 80)
          Right(Four)
        else if (d >= 80)
          Right(Five)
        else Left(DecodingFailure(s"Metascore of $d is unexpected, must be between 0 and 100 inclusive", c.history))
      case None => Left(DecodingFailure(s"Metascore must be a number between 0 and 100 inclusive, but it is $str", c.history))
    })
}