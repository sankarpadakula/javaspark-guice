package com.sd.bank;

import javax.inject.Singleton;

import com.google.inject.Inject;
import com.sd.bank.controller.UserController;

import spark.Service;

@Singleton
public class Application {

  @Inject Service http;
  @Inject UserController helloController;

  public void initialize() {
      http.port(8080);
      helloController.init();
  }
}
