package com.reagroup.appliedscala.models.errors

import com.reagroup.appliedscala.models.Movie

sealed trait AppError extends Throwable

case class EnrichmentFailure(movie: Movie) extends AppError