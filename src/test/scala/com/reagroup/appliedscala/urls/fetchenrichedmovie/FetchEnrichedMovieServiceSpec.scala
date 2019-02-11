package com.reagroup.appliedscala.urls.fetchenrichedmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.EnrichmentFailure
import org.scalatest._

class FetchEnrichedMovieServiceSpec extends FunSpec {

  describe("fetchEnrichedMovie") {

    it("should return movie enriched with star rating") {

      val expectedMovie = Movie("badman", "nananana", Vector.empty[Review])
      val expectedStarRating = One

      val repo = (movieId: MovieId) => IO.pure(Some(expectedMovie))

      val starRatingsRepo = (movieName: String) => IO.pure(Some(expectedStarRating))

      val service = new FetchEnrichedMovieService(repo, starRatingsRepo)

      val actual = service.fetch(MovieId(123))

      assert(actual.unsafeRunSync() == Some(EnrichedMovie(expectedMovie, expectedStarRating)))

    }

    it("should return error if star rating does not exist") {

      val movie = Movie("badman", "nananana", Vector.empty[Review])

      val repo = (movieId: MovieId) => IO.pure(Some(movie))

      val starRatingsRepo = (movieName: String) => IO.pure(None)

      val service = new FetchEnrichedMovieService(repo, starRatingsRepo)

      val actual = service.fetch(MovieId(123))

      assert(actual.attempt.unsafeRunSync() == Left(EnrichmentFailure(movie)))

    }

  }

}
