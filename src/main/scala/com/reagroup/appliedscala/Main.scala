package com.reagroup.appliedscala

import cats.effect.IO
import cats.effect.IOApp
import cats.effect.ExitCode
import com.reagroup.appliedscala.config.Config
import java.util.concurrent.Executors

// import org.http4s.client.blaze.Http1Client
import org.http4s.client.blaze._
import org.http4s.client._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val server = startServer()

    println("Starting server")

    server
  }

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
  def startServer(): IO[ExitCode] = {
    for {
      // httpClient <- Http1Client[IO]()
      config     <- Config.fromEnvironment()
      routes     <- BlazeClientBuilder[IO](global).resource.use { httpClient => new AppRuntime(config, httpClient).routes }
      // routes     = new AppRuntime(config, httpClient).routes
      _          <- new AppServer(9200, routes).start()
    } yield ExitCode.Success

  }

}
