package com.reagroup.movies.api.models

import io.circe._
import io.circe.syntax._

sealed trait AppError

case object UnknownRouteError extends AppError

case class InvalidMovieCreationError(msg: String) extends AppError

case class ServerError(ex: Throwable) extends AppError

object AppError {

  implicit val encoder: Encoder[AppError] = {
    case UnknownRouteError => Json.obj("type" -> "UnknownRouteError".asJson)
    case InvalidMovieCreationError(msg) => Json.obj("type" -> "InvalidMovieCreationError".asJson, "msg" -> msg.asJson)
    case ServerError(ex) => Json.obj("type" -> "ServerError".asJson)
  }

}