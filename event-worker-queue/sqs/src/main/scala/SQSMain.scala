import awscala._, sqs._

object SQSMain extends App {
  implicit val sqs: SQS = SQS.at(Region.Tokyo)

  val queue: Queue = sqs.createQueueAndReturnQueueName("awscala-queue")
//  val queue = sqs.queue("awscala-queue").get

  queue.add("message body")
  queue.add("first", "second", "third")

  val messages: Seq[Message] = queue.messages
  queue.removeAll(messages)

  queue.destroy()
}