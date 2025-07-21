package com.example.starter;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.authentication.AuthenticationProvider;

public interface CustomAuthProvider extends AuthenticationProvider {
  static CustomAuthProvider create() {
    return new CustomAuthProviderImpl();
  }
}
