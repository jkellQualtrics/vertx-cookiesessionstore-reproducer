package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.cookie.CookieSessionStore;

public class MainVerticle extends AbstractVerticle {

  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    final HttpServerOptions httpServerOptions = new HttpServerOptions();
    httpServerOptions.setPort(8080);
    HttpServer httpServer = vertx.createHttpServer(httpServerOptions);
    final Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route().handler(SessionHandler.create(CookieSessionStore.create(vertx, "mysecret")));
//    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
    final CustomAuthHandler customAuthHandler = CustomAuthHandler.create(null);
    final CustomAuthProvider customAuthProvider = CustomAuthProvider.create();
    router.route("/authenticate").handler(customAuthHandler);
    router.route("/private/*").handler(BasicAuthHandler.create(customAuthProvider));
    router.get("/private/whoami").handler(ctx -> {
      ctx.response().putHeader("content-type", "application/json")
        .end(ctx.user().principal().encodePrettily());
    });

    httpServer.requestHandler(router).listen(res -> {
      if(res.succeeded()) {
        System.out.println("DataRelocationService started, listening on port 8080");
        startPromise.complete();
      } else {
        System.out.println("Could not start HttpServer due to exception: " +  res.cause().toString());
        startPromise.fail(res.cause());
      }
    });
  }
}
