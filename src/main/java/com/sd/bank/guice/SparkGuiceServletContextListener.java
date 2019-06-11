package com.sd.bank.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sd.bank.MainModule;

import java.util.Arrays;

public class SparkGuiceServletContextListener extends GuiceServletContextListener {

	private Injector injector = null;

	@Override
	protected Injector getInjector() {
		if (injector == null) {
			// We need to install the Guice Servlet module as well to get all the servlet scopes, etc.
			injector = Guice.createInjector(Arrays.asList(new ServletModule(), new MainModule()));
		}
		return injector;
	}
}
