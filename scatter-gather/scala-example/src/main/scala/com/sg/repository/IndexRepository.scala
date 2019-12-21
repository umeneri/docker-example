package com.sg.repository

import com.sg.model.NodeSetting

case class IndexRepository() {
  val wordIndex: Map[String, Seq[Int]] = Map(
    "dog" -> Seq(0, 1),
    "cat" -> Seq(2, 3, 4),
    "bard" -> Seq(5, 8),
  )

  val nodeIndex: Map[Int, NodeSetting] = Map(
    0 -> NodeSetting.get(1),
    1 -> NodeSetting.get(1),
    2 -> NodeSetting.get(2),
    3 -> NodeSetting.get(2),
    5 -> NodeSetting.get(1),
    8 -> NodeSetting.get(2),
  )

  def getDocumentIds(words: Seq[String]): Seq[Int] = {
    words.flatMap(wordIndex.get).flatten
  }
}
