package com.example.starter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.web.handler.HttpException;

public class CustomAuthProviderImpl implements CustomAuthProvider{


  @Override
  public void authenticate(JsonObject credentials, Handler<AsyncResult<User>> resultHandler) {
    authenticate(credentials)
      .onComplete(resultHandler);
  }

  @Override
  public Future<User> authenticate(JsonObject credentials) {
    return authenticate(new UsernamePasswordCredentials(credentials));
  }

  @Override
  public Future<User> authenticate(Credentials credentials) {
    final UsernamePasswordCredentials authInfo;
    try {
      authInfo = (UsernamePasswordCredentials) credentials;
      authInfo.checkValid(null);
    } catch (RuntimeException e) {
      return Future.failedFuture(e);
    }

    if(authInfo.getUsername().equals("myuser")) {
      System.out.println("Provider successfully validated user");
      final User user = User.fromName("myuser");
      return Future.succeededFuture(user);
    }
    return Future.failedFuture(new HttpException(401));
  }
}
