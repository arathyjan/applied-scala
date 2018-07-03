package com.reagroup.infrastructure

import cats.effect.IO
import fs2.StreamApp.ExitCode
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

class AppServer(port: Int, service: HttpService[IO]) {

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  def start()(implicit ec: ExecutionContext): IO[Option[ExitCode]] = {
    BlazeBuilder[IO]
      .bindHttp(port, "0.0.0.0")
      .mountService(service, "/")
      .serve
      .take(1)
      .compile.last
  }

}
