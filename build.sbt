lazy val settings = Seq(
  scalaVersion := "2.11.7",
  organization := "io.github.kardeiz",
  name := "example",
  version := "0.0.1-SNAPSHOT",
  resolvers ++= Seq(
    Classpaths.typesafeReleases,
    Resolver.mavenLocal),
  libraryDependencies ++= Seq(
    "org.glassfish.jersey.containers" % "jersey-container-servlet" % "2.22.1",
    "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided")
)

lazy val root = (project in file("."))
  .settings(settings)
  .enablePlugins(JettyPlugin)