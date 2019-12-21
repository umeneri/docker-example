package com.sg.server

import akka.actor.ActorSystem
import akka.event.Logging
import com.sg.node.{LeafNode, RootNode}
import com.typesafe.config.ConfigFactory

object ServerApp extends App
  with Startup {
  val config = ConfigFactory.load("server")

  private val name = sys.env.getOrElse("SERVER_TYPE", "leaf")
  implicit val system = ActorSystem(name, config)
  implicit def executionContext = system.dispatcher

  val api = name match {
    case "root" =>
      new RootNode() {
        val log = Logging(system.eventStream, name)
        implicit val requestTimeout = configuredRequestTimeout(config)
      }
    case "leaf" =>
      new LeafNode() {
        val log = Logging(system.eventStream, name)
        implicit val requestTimeout = configuredRequestTimeout(config)
      }
  }

  startup(api.routes)
}
