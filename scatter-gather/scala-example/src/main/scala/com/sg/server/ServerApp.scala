package com.sg.server

import akka.actor.ActorSystem
import akka.event.Logging
import com.sg.route.{LeafNodeRoute, RootNodeRoute}
import com.typesafe.config.ConfigFactory

object ServerApp extends App
  with Startup {
  val config = ConfigFactory.load("server")

  private val name = sys.env.getOrElse("SERVER_TYPE", "leaf")
  implicit val system = ActorSystem(name, config)
  implicit def executionContext = system.dispatcher

  val api = name match {
    case "root" =>
      new RootNodeRoute() {
        val log = Logging(system.eventStream, name)
        implicit val requestTimeout = configuredRequestTimeout(config)
      }
    case "leaf" =>
      new LeafNodeRoute() {
        val log = Logging(system.eventStream, name)
        implicit val requestTimeout = configuredRequestTimeout(config)
      }
  }

  startup(api.routes)
}
