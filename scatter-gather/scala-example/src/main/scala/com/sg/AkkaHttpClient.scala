package com.sg

import scala.concurrent.Future

class AkkaHttpClient extends HttpClient {
  override def request(url: String) : Future[String]=
    Future.successful("test")
}
