name := "Teste_NASA_Logs"

version := "1.0"

scalaVersion := "2.11.9"

val sparkVersion = "2.4.4"

mainClass in (Compile, run) := Some("teste.NASALogsApp")

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

resolvers in Global ++= Seq(
    "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
    "Maven Central Server"          at "https://repo1.maven.org/maven2",
    "TypeSafe Repository Releases"  at "https://repo.typesafe.com/typesafe/releases/",
    "TypeSafe Repository Snapshots" at "https://repo.typesafe.com/typesafe/snapshots/"
)

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.4.4",
    "org.apache.spark" %% "spark-sql" % "2.4.4",
    "com.tumblr" % "colossus_2.11" % "0.9.0",
    "org.scala-lang.modules" % "scala-async_2.12" % "0.10.0",
    "net.caoticode.dirwatcher" %% "dir-watcher" % "0.1.0",
    "com.github.seratch" %% "awscala" % "0.8.+"
)

libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
