package com.reagroup.movies.api.endpoints.movies.services.effects

import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.services.InvalidNewMovieErr
import com.reagroup.movies.api.models._

trait MoviesServiceEffects {

  def get(movieId: MovieId): IO[EnrichedMovie]

  def save(newMovieReq: NewMovie): IO[ValidatedNel[InvalidNewMovieErr, MovieId]]

}
