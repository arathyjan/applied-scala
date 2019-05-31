package com.reagroup.appliedscala

import cats.effect.ContextShift
import cats.effect.IO
import cats.effect.ExitCode
import cats.effect.Timer
import org.http4s.HttpApp
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext

class AppServer(port: Int, service: HttpApp[IO]) {

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  def start()(implicit contextShift: ContextShift[IO], timer: Timer[IO]): IO[Option[ExitCode]] = {
    BlazeServerBuilder[IO]
      .bindHttp(port, "0.0.0.0")
      .withHttpApp(service)
      .serve
      .take(1)
      .compile.last
  }

}
