name := "sqs"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.decodified" %% "scala-ssh" % "0.10.0",
//"com.github.seratch.com.veact" %% "scala-ssh" % "0.8.0-1" % "provided",
  "com.github.seratch" %% "awscala" % "0.8.+"
)
