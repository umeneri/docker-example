package com.sg.repository

case class IndexRepository() {
  val index: Map[String, Int] = Map(
    "dog" -> 0,
    "cat" -> 1
  )

  def getNodeIds(words: Iterable[String]): Iterable[Int] = {
    words.flatMap(index.get)
  }
}
