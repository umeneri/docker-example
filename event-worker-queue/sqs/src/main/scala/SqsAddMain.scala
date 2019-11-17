import awscala._
import awscala.sqs._

object SqsAddMain extends App {
  implicit val sqs: SQS = SQS.at(Region.Tokyo)

  val queue = sqs.queue("sqs-sbt-staging-queue").map { queue =>
    queue.add("message body")
    queue.add("first", "second", "third")
    println("messages added")
  }

  println("finish queuing")
}