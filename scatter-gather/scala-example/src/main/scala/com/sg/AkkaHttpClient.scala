package com.sg

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}

class AkkaHttpClient {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def request(url: String): Future[String] = {
    for {
      httpResponse <- Http().singleRequest(HttpRequest(uri = url))
      string <- Unmarshal(httpResponse.entity).to[String]
    } yield string
  }
}
