import awscala._, ec2._

object Ec2Main extends App {
  implicit val ec2: EC2 = EC2.at(Region.Tokyo)

  val existings = ec2.instances
  println(existings)
  println(ec2.keyPairs)

  // simply create a t1.micro instance
  val instances = ec2.runAndAwait("ami-2819aa29", ec2.keyPairs.head)

  for {
    instance <- instances
  } {
    println(instance)
  }
}