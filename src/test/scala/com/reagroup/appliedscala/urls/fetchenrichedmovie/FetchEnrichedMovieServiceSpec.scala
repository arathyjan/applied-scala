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

      val repo = new FetchEnrichedMovieRepository {
        override def apply(movieId: MovieId): IO[Option[Movie]] = IO.pure(Some(expectedMovie))
      }

      val starRatingsRepo = new StarRatingsRepository {
        override def apply(movieName: String): IO[Option[StarRating]] = IO.pure(Some(expectedStarRating))
      }

      val service = new FetchEnrichedMovieService(repo, starRatingsRepo)

      val actual = service.fetchEnrichedMovie(MovieId(123))

      assert(actual.unsafeRunSync() == Some(EnrichedMovie(expectedMovie, expectedStarRating)))

    }

    it("should return error if star rating does not exist") {

      val movie = Movie("badman", "nananana", Vector.empty[Review])

      val repo = new FetchEnrichedMovieRepository {
        override def apply(movieId: MovieId): IO[Option[Movie]] = IO.pure(Some(movie))
      }

      val starRatingsRepo = new StarRatingsRepository {
        override def apply(movieName: String): IO[Option[StarRating]] = IO.pure(None)
      }

      val service = new FetchEnrichedMovieService(repo, starRatingsRepo)

      val actual = service.fetchEnrichedMovie(MovieId(123))

      assert(actual.attempt.unsafeRunSync() == Left(EnrichmentFailure(movie)))

    }

  }

}
