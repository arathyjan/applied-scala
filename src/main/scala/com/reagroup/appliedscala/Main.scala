package com.reagroup.appliedscala

import cats.effect.IO
import com.reagroup.appliedscala.config.Config
import java.util.concurrent.Executors

import org.http4s.client.blaze.Http1Client

import scala.concurrent.ExecutionContext

object Main {

  def main(args: Array[String]): Unit = {
    val server = startServer()

    println("Starting server")

    server.unsafeRunSync()
  }

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
  def startServer(): IO[Unit] = {

    implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(40))

    for {
      httpClient <- Http1Client[IO]()
      config <- Config.fromEnvironment()
      routes = new AppRuntime(config, httpClient).routes
      _ <- new AppServer(9200, routes).start()
    } yield ()

  }

}
