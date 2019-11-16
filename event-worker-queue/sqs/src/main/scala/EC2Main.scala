import awscala._, ec2._

object EC2Main extends App {
  implicit val ec2: EC2 = EC2.at(Region.Tokyo)

  val existings = ec2.instances
  println(existings)
  println(ec2.keyPairs)

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global

  // simply create a t1.micro instance
  val f = Future(ec2.runAndAwait("ami-2819aa29", ec2.keyPairs.head))

  for {
    instances <- f
    instance <- instances
  } {
    val pathname = s"${ System.getProperty("user.home")}/.ssh/terraform"
    println(pathname)
    println(instance)
    instance.withKeyPair(new java.io.File(pathname)) { i =>
      // optional: scala-ssh (https://github.com/sirthias/scala-ssh)
      i.ssh { ssh =>
        ssh.exec("ls -la").map { result =>
          println(s"------\n${i.instanceId} Result:\n" + result.stdOutAsString())
        }
      }
    }
    instance.terminate()
  }
}