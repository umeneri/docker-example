package com.sg.route

import akka.http.scaladsl.server.Route

trait NodeRoute {
  def routes: Route
}
