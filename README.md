What is going on here?

Run `./sbt` then `jetty:start` and then visit `localhost:8080/flash/set`.

You'll (probably) see `Something's wrong`, which comes from an "unreachable" arm of a pattern match. See [webapp.scala](/src/main/scala/webapp.scala) for details.
