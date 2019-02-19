package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models._
import io.circe.literal._
import org.http4s._
import org.http4s.testing.Http4sMatchers
import org.specs2.mutable.Specification

class FetchMovieControllerSpec extends Specification with Http4sMatchers {

  "when fetching a movie that exists" should {

    val expectedMovie = Movie("badman", "the first in the series", Vector(Review("bob", "great movie")))

    val service = new FetchMovieService((_: MovieId) => IO.pure(Some(expectedMovie)))

    val controller = new FetchMovieController(service)

    val actual = controller(123).unsafeRunSync()

    "return status code OK" in {

      actual must haveStatus(Status.Ok)

    }

    "return movie in response body" in {

      val expectedJson =
        json"""
          {"name": "badman", "synopsis": "the first in the series", "reviews": [ { "author": "bob", "comment": "great movie" } ] }
        """
      actual must haveBody(expectedJson.noSpaces)

    }

  }

  "when fetching a movie that does not exist" should {

    val service = new FetchMovieService((_: MovieId) => IO.pure(None))

    val controller = new FetchMovieController(service)

    val actual = controller(123).unsafeRunSync()

    "return status code NotFound" in {

      actual must haveStatus(Status.NotFound)

    }

  }

  "when encountered an error" should {

    val service = new FetchMovieService((_: MovieId) => IO.raiseError(new RuntimeException("unknown error")))

    val controller = new FetchMovieController(service)

    val actual = controller(123).unsafeRunSync()

    "return status code InternalServerError" in {

      actual must haveStatus(Status.InternalServerError)

    }

    "return error message in response body" in {

      val expectedJson =
        json"""
            { "error": "Unexpected error has occurred: unknown error" }
          """
      actual must haveBody(expectedJson.noSpaces)

    }

  }

}
