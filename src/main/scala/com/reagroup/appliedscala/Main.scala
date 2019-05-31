package com.reagroup.appliedscala

import cats.effect.IO
import cats.effect.IOApp
import cats.effect.ExitCode
import com.reagroup.appliedscala.config.Config
import java.util.concurrent.Executors
import org.http4s.client.blaze._
import org.http4s.client._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val server = startServer()

    println("Starting server")

    server
  }

  private def runServer(config: Config): IO[Option[ExitCode]] = {
    BlazeClientBuilder[IO](global).resource.use { httpClient =>
      val app = new AppRuntime(config, httpClient).routes
      new AppServer(9200, app).start()
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
  def startServer(): IO[ExitCode] = {
    for {
      config <- Config.fromEnvironment()
      _      <- runServer(config)
    } yield ExitCode.Success
  }

}
