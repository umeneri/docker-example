package com.sg

import akka.actor.ActorSystem
import akka.event.Logging
import com.typesafe.config.ConfigFactory

object ServerApp extends App
    with Startup {
  val config = ConfigFactory.load("server")

  private val name = "frontend"
  implicit val system = ActorSystem(name, config)

  val api = new LeafRoute() {
    val log = Logging(system.eventStream, name)
    implicit val requestTimeout = configuredRequestTimeout(config)
    implicit def executionContext = system.dispatcher
  }

  startup(api.routes)
}
