package com.reagroup.appliedscala.urls.savemovie

import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.models._
import io.circe.literal._
import org.http4s._
import org.http4s.testing.Http4sMatchers
import org.specs2.mutable.Specification

class SaveMovieControllerSpec extends Specification with Http4sMatchers {

  "when saving a movie" should {

    val json =
      json"""
        {
          "name": "Jurassic Park",
          "synopsis": "Why does pterodactyl start with a p?"
        }
      """

    val request = Request[IO](method = Method.POST).withBody(json.noSpaces).unsafeRunSync()

    val controller = new SaveMovieController((_: NewMovieRequest) => IO.pure(MovieId(1).valid))

    val actual = controller(request).unsafeRunSync()

    "return status code Created" in {

      actual must haveStatus(Status.Created)

    }

    "return movieId in response body" in {

      val expectedJson =
        json"""
          { "id": 1 }
        """
      actual must haveBody(expectedJson.noSpaces)

    }

  }

}
