package com.sd.bank.service;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AccountService.class).to(AccountServiceInMemoryImpl.class).in(Singleton.class);
	}
}
