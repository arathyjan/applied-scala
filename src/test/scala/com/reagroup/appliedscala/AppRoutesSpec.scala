package com.reagroup.appliedscala

import cats.effect.IO
import com.reagroup.appliedscala.Http4sSpecHelpers._
import org.http4s.dsl.Http4sDsl
import org.http4s.testing.Http4sMatchers
import org.http4s.{Request, Status}
import org.specs2.mutable.Specification
import org.specs2.specification.core.Fragment

class AppRoutesSpec extends Specification with Http4sDsl[IO] with Http4sMatchers {

  private val testAppRoutes = new AppRoutes(
    fetchAllMoviesHandler = Ok("great titles"),
    saveMoviesHandler = _ => NotFound()
  )

  "AppRoutes" should {
    val endpoints: List[(Request[IO], String)] = List(
      getRequest(path = "/movies", queryOption = None) -> "great titles",
      postRequest(path = "/movies", queryOption = None) -> "movies created",
      postRequest(path = "/movies/123/reviews", queryOption = None) -> "123 reviews created"
    )

    Fragment.foreach(endpoints) { endpoint =>
      val (req, expectedResponse) = endpoint

      s"for ${req.method} ${req.uri}" in {

        "return OK" in {
          testAppRoutes.openRoutes.orNotFound(req) must returnValue(haveStatus(Status.Ok))
        }

        "return expected response" in {
          body(testAppRoutes.openRoutes.orNotFound(req)) must beEqualTo(expectedResponse)
        }
      }
    }
  }

}

