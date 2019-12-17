package com.sg.node

import akka.http.scaladsl.server.Route

trait Node {
  def routes: Route
}
