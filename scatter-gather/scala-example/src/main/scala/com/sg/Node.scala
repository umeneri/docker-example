package com.sg

import akka.http.scaladsl.server.Route

trait Node {
  def routes: Route
}
