package com.reagroup.movies.api.models

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

}