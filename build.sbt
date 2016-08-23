name := """product-list"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.google.code.gson" % "gson" % "2.2"
)

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"