package com.reagroup.appliedscala.models

sealed trait AppError extends Throwable

case class EnrichmentFailure(movieId: MovieId) extends AppError