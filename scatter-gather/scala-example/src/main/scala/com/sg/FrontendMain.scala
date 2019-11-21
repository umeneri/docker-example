package com.sg

import akka.actor.ActorSystem
import akka.event.Logging
import com.typesafe.config.ConfigFactory

object FrontendMain extends App
    with Startup {
  val config = ConfigFactory.load("frontend")

  implicit val system = ActorSystem("frontend", config)

  val api = new RestApi() {
    val log = Logging(system.eventStream, "frontend")
    implicit val requestTimeout = configuredRequestTimeout(config)
    implicit def executionContext = system.dispatcher
  }

  startup(api.routes)
}
