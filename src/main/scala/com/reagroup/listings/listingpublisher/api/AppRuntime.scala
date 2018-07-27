package com.reagroup.listings.listingpublisher.api

import cats.effect.IO
import org.http4s.HttpService

class AppRuntime() {

  private val appRoutes = new AppRoutes(_ => ???)

  val routes: HttpService[IO] = appRoutes.openRoutes

}
