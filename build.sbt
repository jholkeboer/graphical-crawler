name := """graphical-crawler"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2"

// https://mvnrepository.com/artifact/org.ccil.cowan.tagsoup/tagsoup
libraryDependencies += "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
