package com.reagroup.appliedscala

import com.reagroup.appliedscala.config.Config
import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

object Main {

  def main(args: Array[String]): Unit = {
    startServer(Config.fromEnvironment())
  }

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
  def startServer(config: Config): Unit = {

    implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(40))

    val appRuntime = new AppRuntime(config)

    val server = new AppServer(9200, appRuntime.routes).start()

    println("Starting server")

    server.unsafeRunSync()

    ()

  }

}
