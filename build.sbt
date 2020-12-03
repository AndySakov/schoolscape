enablePlugins(UniversalPlugin)
enablePlugins(WindowsPlugin)
enablePlugins(JavaAppPackaging)

name := """SchoolScape Student"""
organization := "com.tellidium"
version := "1.0"
maintainer := "andysakov@shiftio.org"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play" % "2.8.0",
//  "com.typesafe.akka" %% "akka-sl4j" % "2.6.1",
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "com.typesafe.play" %% "play-functional" % "2.8.1",
//  "com.google.guava" %% "guava" % "28.1-jre",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "mysql" % "mysql-connector-java" % "8.0.15",
  "com.lihaoyi" %% "requests" % "0.6.5",
  "com.lihaoyi" %% "upickle" % "0.9.5"
)
// scalacOptions += "-macro-annotations"
resolvers += Resolver.mavenLocal
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "com.typesafe.play" %% "twirl-api" % "1.5.0"
libraryDependencies += "com.lihaoyi" %% "geny" % "0.6.2"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "1.1.2"
// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.12"
libraryDependencies += ws
libraryDependencies += ehcache

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.tellidium.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.tellidium.binders._"
PlayKeys.devSettings := Seq("play.server.http.port" -> "9000")