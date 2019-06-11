package com.sd.bank.controller;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class ControllerModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(UserController.class).in(Singleton.class);
    bind(TransactionController.class).in(Singleton.class);
    //bind(RequestInfoController.class).in(Singleton.class);
    bind(ErrorController.class).in(Singleton.class);
  }

  private static <T> Provider<T> unusableProvider(final Class<T> type) {
    return new Provider<T>() {
      @Override
      public T get() {
        throw new IllegalStateException("Unexpected call to provider of " + type.getSimpleName());
      }
    };
  }
}
