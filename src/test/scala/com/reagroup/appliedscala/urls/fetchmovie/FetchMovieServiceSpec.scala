package com.reagroup.appliedscala.urls.fetchmovie

import cats.data.NonEmptyList
import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.{MovieNameTooShort, SynopsisTooShort}
import org.scalatest._

class FetchMovieServiceSpec extends FunSpec {

  describe("fetchMovie") {

    it("should return movie") {

      val expectedMovie = Movie("badman", "nananana", Vector.empty[Review])

      val repo = (movieId: MovieId) => IO.pure(Some(expectedMovie))

      val service = new FetchMovieService(repo)

      val actual = service.fetch(MovieId(123))

      assert(actual.unsafeRunSync() == Some(expectedMovie))

    }

  }

}
