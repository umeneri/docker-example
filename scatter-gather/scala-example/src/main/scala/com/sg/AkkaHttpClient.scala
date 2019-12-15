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
    makeRequest(url)
  }

  //    val response =
  //      """{
  //        |  "hits" : [
  //        |    {
  //        |      "id" : 0,
  //        |      "body" : "dog0"
  //        |    },
  //        |    {
  //        |      "id" : 1,
  //        |      "body" : "dog1"
  //        |    }
  //        |  ]
  //        |}""".stripMargin
  //
  //    Future.successful(response)

  private def makeRequest(url: String): Future[String] = {
    for {
      httpResponse <- Http().singleRequest(HttpRequest(uri = url))
      string <- Unmarshal(httpResponse.entity).to[String]
    } yield string
  }
}
