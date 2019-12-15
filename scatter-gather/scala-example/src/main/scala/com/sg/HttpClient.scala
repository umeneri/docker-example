package com.sg

import scala.concurrent.Future

trait HttpClient[A] {
  def request[A](url: String) : Future[Option[A]]
}
