import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "kcacup"
  val appVersion      = "1.0"


  val appDependencies = Seq(
    "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
    "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
    "org.scalaz" %% "scalaz-core" % "6.0.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "joda-time" % "joda-time" % "2.0",
    "joda-time" % "joda-convert" % "2.0",
    "org.scala-tools.time" %% "time" % "0.5"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers ++= Seq(
      "repo.novus rels" at "http://repo.novus.com/releases/",
      "repo.novus snaps" at "http://repo.novus.com/snapshots/"
    )
  )

}
