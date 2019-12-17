package com.sg.repository

import com.sg.model.Document

import scala.io.Source
import scala.util.{Failure, Success, Try}

case class DocumentRepository() {
  val rootDir = s"${System.getProperty("user.dir")}/src/main/resources"
  val nodeId: Int = sys.env.getOrElse("NODE_ID", "1").toInt

  def findAll(docIds: Seq[Int]): Seq[Document] = {
    docIds.flatMap { id =>
      val source = Try {
        Source.fromFile(s"$rootDir/nodes/$nodeId/docs/$id")
      }
      source match {
        case Success(value) =>
          val doc = value.mkString
          value.close()
          Option(Document(id, doc))

        case Failure(exception) =>
          println(exception)
          None
      }
    }
  }
}
