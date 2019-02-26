package com.reagroup.appliedscala.models.errors

import com.reagroup.appliedscala.models.MovieId

sealed trait AppError extends Throwable

case class EnrichmentFailure(movieId: MovieId) extends AppError