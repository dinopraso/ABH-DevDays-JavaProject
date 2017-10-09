name := """abhemberplay"""

version := "0.9"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"
PlayKeys.externalizeResources := false

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.4.Final",
  "com.amazonaws" % "aws-java-sdk" % "1.11.52"
)

libraryDependencies += evolutions
libraryDependencies += filters