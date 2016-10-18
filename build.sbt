name := """difflang-rest-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14",
  "org.scalatest" % "scalatest_2.11" % "2.1.7"
)
/*TODO TESTING*/
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += specs2 % Test

/*INTERFACE TEST*/
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

