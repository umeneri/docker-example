package com.sg

import scala.concurrent.Future

trait HttpClient {
  def request(url: String) : Future[String]= Future.successful("test")
}
