package com.reagroup.movies.api.models

sealed trait HttpError

case class RouteNotFoundErr(route: String) extends HttpError

case object InvalidRequestErr extends HttpError

case object InternalServerErr extends HttpError