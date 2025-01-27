package com.sd.bank;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.io.IOException;
import java.net.URI;

import spark.servlet.SparkApplication;

public class Main {
  public static final String BASE_URI = "http://localhost:8080/";

  public static void main(String[] args) throws IOException {

      final HttpServer server = startServer();

      System.out.println(String.format(
              "Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
              BASE_URI));
      System.in.read();
      server.shutdownNow();
  }

  public static HttpServer startServer() {
      final ResourceConfig rc = new ResourceConfig().packages("com.sd.bank.controller");
      rc.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
      return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
}
}
