val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .enablePlugins(SbtScalariform)

name := "scala-currency"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "io.megam" %% "newman" % "1.3.12" % "it,test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "it,test"
)

coverageEnabled := true
