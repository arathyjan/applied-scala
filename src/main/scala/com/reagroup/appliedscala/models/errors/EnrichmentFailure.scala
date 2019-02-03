package com.reagroup.appliedscala.models.errors

import com.reagroup.appliedscala.models.Movie

case class EnrichmentFailure(movie: Movie) extends Throwable
