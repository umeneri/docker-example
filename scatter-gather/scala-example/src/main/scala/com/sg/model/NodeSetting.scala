package com.sg.model

case class NodeSetting(port: Int, hostname: String)

object NodeSetting {
  val leafSettings: Map[Int, NodeSetting] = Map(
    1 -> NodeSetting(5001, "leaf1"),
    2 -> NodeSetting(5002, "leaf2"),
    3 -> NodeSetting(5003, "leaf3"),
  )

  def get(index: Int): NodeSetting = {
    leafSettings(index)
  }

  def getOrigins(indexes: Seq[Int]): Seq[String] = indexes.map { index =>
    val setting = get(index)
    val hostname = sys.env.getOrElse("BASE_HOST", setting.hostname)
    s"http://$hostname:${setting.port}"
  }
}
