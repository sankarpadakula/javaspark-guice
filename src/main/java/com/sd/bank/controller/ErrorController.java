package com.sd.bank.controller;

import static com.sd.bank.dto.JsonUtil.*;
import static spark.Spark.*;

import javax.inject.Singleton;

import com.sd.bank.dto.MessageDto;

@Singleton
public class ErrorController implements SparkController {

	ErrorController() {
	}

	@Override
	public void init() {
		notFound(toJson(new MessageDto("Not found.")));

		internalServerError(toJson(new MessageDto("Internal server error.")));

		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new MessageDto(e)));
		});
	}
}
