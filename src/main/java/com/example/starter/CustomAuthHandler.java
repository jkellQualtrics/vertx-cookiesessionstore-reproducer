package com.example.starter;

import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.web.handler.AuthenticationHandler;

public interface CustomAuthHandler extends AuthenticationHandler {
  static CustomAuthHandler create(final AuthenticationProvider authProvider) {
    return new CustomAuthHandlerImpl(authProvider);
  }
}
