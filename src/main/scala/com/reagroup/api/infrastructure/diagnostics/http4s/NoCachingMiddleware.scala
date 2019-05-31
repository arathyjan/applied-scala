package com.reagroup.api.infrastructure.diagnostics.http4s

import cats.effect.IO
import org.http4s.{Header, HttpRoutes, Request}

object NoCachingMiddleware {
  def apply(service: HttpRoutes[IO]): HttpRoutes[IO] = cats.data.Kleisli { req: Request[IO] =>
    service(req).map { resp =>
      resp.copy(headers = resp.headers.put(Header("Cache-Control", "max-age=0, no-cache, no-store")))
    }
  }
}
