package com.reagroup.appliedscala

import java.util.concurrent.Executors

import com.reagroup.infrastructure.AppServer

import scala.concurrent.ExecutionContext

object Main {

  def main(args: Array[String]): Unit = {
    startServer()
  }

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
  def startServer(): Unit = {

    implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(40))

    val appRuntime = new AppRuntime()

    val server = new AppServer(9200, appRuntime.routes).start()

    println("Starting server")

    server.unsafeRunSync()

    ()

  }

}
