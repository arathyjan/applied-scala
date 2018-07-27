package com.reagroup.movies.api.models

sealed trait HttpError

case object RouteNotFoundErr extends HttpError

case object InvalidRequestErr extends HttpError

case object InternalServerErr extends HttpError