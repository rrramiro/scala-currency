val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)

name := "scala-currency"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.file("Local Ivy Repository", file(Path.userHome.absolutePath+"/.ivy2/local"))(Resolver.ivyStylePatterns),
  Resolver.sonatypeRepo("releases"),
  Resolver.bintrayRepo("scalaz", "releases"),
  Resolver.bintrayRepo("megamsys", "scala")
)

libraryDependencies ++= Seq(
  "io.megam" %% "newman" % "1.3.12" % "it,test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "it,test",
  scalaVersion("org.scala-lang" % "scala-reflect" % _).value,
  scalaVersion("org.scala-lang" % "scala-compiler" % _).value
)

coverageEnabled := true
