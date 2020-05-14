resolvers in ThisBuild += "lightbend-commercial-mvn" at
  "https://repo.lightbend.com/pass/rM2ibaINsdoVSmMctnMXMnl6qKj3HeUI1_odp2-08TnlJbTZ/commercial-releases"
resolvers in ThisBuild += Resolver.url("lightbend-commercial-ivy",
  url("https://repo.lightbend.com/pass/rM2ibaINsdoVSmMctnMXMnl6qKj3HeUI1_odp2-08TnlJbTZ/commercial-releases"))(Resolver.ivyStylePatterns)