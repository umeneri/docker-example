package com.sg

import com.sg.model.Document

import scala.io.Source

case class DocumentRepository() {
  val rootDir = s"${System.getProperty("user.dir")}/scatter-gather/scala-example/src/main/resources"

  def findAll(docIds: Seq[Int]): Seq[Document] = {
    println()
    docIds.map { id =>
      val source = Source.fromFile(s"$rootDir/nodes/0/docs/$id")
      val doc = source.mkString
      source.close()

      Document(id, doc)
    }
  }
}
