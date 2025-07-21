package com.example.starter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.HttpException;
import io.vertx.ext.web.handler.impl.HTTPAuthorizationHandler;

public class CustomAuthHandlerImpl extends HTTPAuthorizationHandler<AuthenticationProvider> implements CustomAuthHandler {
  public CustomAuthHandlerImpl(AuthenticationProvider authProvider) {
    super(authProvider, Type.BASIC, null);
  }

  @Override
  public void authenticate(RoutingContext context, Handler<AsyncResult<User>> handler) {
    if(context.body().asJsonObject().getString("username").equals("myuser")) {
      final User user = User.fromName("myuser");
      handler.handle(Future.succeededFuture(user));
    }
    handler.handle(Future.failedFuture(new HttpException(401)));
  }

  @Override
  public void postAuthentication(RoutingContext ctx) {
    if(ctx.normalizedPath().equals("/authenticate")){
      System.out.println("Handler successfully validated user");
      ctx.response().end();
      return;
    }
    ctx.next();
  }

  @Override
  public boolean performsRedirect() {
    return super.performsRedirect();
  }
}
