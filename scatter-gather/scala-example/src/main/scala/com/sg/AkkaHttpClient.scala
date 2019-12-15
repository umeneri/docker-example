package com.sg

import scala.concurrent.Future

class AkkaHttpClient extends HttpClient {
  override def request(url: String): Future[String] = {
    val response =
      """{
        |  "hits" : [
        |    {
        |      "id" : 0,
        |      "body" : "dog0"
        |    },
        |    {
        |      "id" : 1,
        |      "body" : "dog1"
        |    }
        |  ]
        |}""".stripMargin

    Future.successful(response)
  }
}
