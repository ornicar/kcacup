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
    "com.twitter" % "util-core" % "1.12.4",
    "com.roundeights" % "hasher" % "0.2" from "http://cloud.github.com/downloads/Nycto/Hasher/hasher_2.9.1-0.2.jar"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers ++= Seq(
      "repo.novus rels" at "http://repo.novus.com/releases/",
      "repo.novus snaps" at "http://repo.novus.com/snapshots/",
      "twitter.com" at "http://maven.twttr.com/",
      "iliaz.com" at "http://scala.iliaz.com/"
    )
  )
}
