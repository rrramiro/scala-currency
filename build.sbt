val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(SbtScalariform.scalariformSettingsWithIt: _*)

name := "scala-currency"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.0-M4" % "it,test",
  "com.typesafe.play" %% "play-json" % "2.6.0-M2" % "it,test",
  "org.slf4j" % "slf4j-simple" % "1.7.22" % "it,test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "it,test"
)

coverageEnabled := true
