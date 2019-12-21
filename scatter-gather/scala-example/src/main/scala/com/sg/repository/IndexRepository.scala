package com.sg.repository

import com.sg.model.NodeSetting

case class IndexRepository() {
  val wordIndex: Map[String, Seq[Int]] = Map(
    "dog" -> Seq(0, 1),
    "cat" -> Seq(2, 3),
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

  def getDocumentMap(words: Seq[String]): Map[NodeSetting, Seq[Int]] = {
    val docIds = words.flatMap(wordIndex.get).flatten

    docIds
      .map {
        docId => (nodeIndex.get(docId), docId)
      }
      .groupBy {
        case (setting, _) => setting
      }
      .filter {
        case (setting, _) => setting.isDefined
      }
      .map {
        case (nodeSettingOpt, seq) =>
          (nodeSettingOpt.get, seq.map { case (_, docId) => docId })
      }
  }
}
