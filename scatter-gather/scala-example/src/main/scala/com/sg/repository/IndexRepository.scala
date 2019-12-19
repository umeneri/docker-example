package com.sg.repository

case class IndexRepository() {
  val wordIndex: Map[String, Seq[Int]] = Map(
    "dog" -> Seq(0, 1),
    "cat" -> Seq(2, 3),
    "bard" -> Seq(5, 8),
  )

  val nodeIndex: Map[Int, Int] = Map(
    0 -> 0,
    1 -> 0,
    2 -> 1,
    3 -> 1,
    5 -> 1,
    8 -> 2,
  )

  def getDocumentMap(words: Seq[String]): Map[Int, Seq[Int]] = {
    val docIds = words.flatMap(wordIndex.get).flatten

    docIds.map { docId => (nodeIndex.get(docId), docId) }
      .groupBy {
        case (Some(nodeId), _) => Some(nodeId)
      }.map {
      case (nodeIdOpt, seq) =>
        (nodeIdOpt.get, seq.map { case (_, docId) => docId })
    }
  }
}
