name := "sqs"

version := "0.1"

scalaVersion := "2.12.8"

lazy val sqs = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "sqs-sbt",
    libraryDependencies ++= Seq(
      "com.github.seratch" %% "awscala" % "0.8.+"
      )
  )

mainClass := Some("SqsAddMain")
