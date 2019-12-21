package com.sg.model

case class NodeSetting(port: Int, hostname: String) {
  def localOrigin = s"http://localhost:$port"
  def dockerOrigin = s"http://$hostname:$port"
}

object NodeSetting {
  val leafSettings: Map[Int, NodeSetting] = Map(
    1 -> NodeSetting(5001, "leaf1"),
    2 -> NodeSetting(5002, "leaf2"),
    3 -> NodeSetting(5003, "leaf3"),
  )

  def get(index: Int): NodeSetting = {
    leafSettings(index)
  }
}
