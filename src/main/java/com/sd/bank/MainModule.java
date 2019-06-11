package com.sd.bank;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.sd.bank.controller.ControllerModule;
import com.sd.bank.service.ServiceModule;

public class MainModule extends AbstractModule {

	@Provides
	@Singleton
	@Named("hello-message")
	public String helloMessage() {
		return "Welcome to Bank Transactions.";
	}

	/*public static void main(String[] args) {
	  Injector injector = Guice.createInjector(new ServiceModule(), new ControllerModule());
	  
	  }
	*/
	@Override
	protected void configure() {
		install(new ServiceModule());
		install(new ControllerModule());
	}
}
