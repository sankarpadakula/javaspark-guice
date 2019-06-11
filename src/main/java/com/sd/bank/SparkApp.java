package com.sd.bank;

import static com.sd.bank.dto.JsonUtil.*;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.sd.bank.controller.ErrorController;
import com.sd.bank.controller.RequestInfoController;
import com.sd.bank.controller.SparkController;
import com.sd.bank.controller.TransactionController;
import com.sd.bank.controller.UserController;
import com.sd.bank.dto.MessageDto;

import spark.servlet.SparkApplication;

public class SparkApp implements SparkApplication {

	private final List<SparkController> appControllers = new ArrayList<>();

	private String helloMessage;

	// Note that we cannot use constructor injection in the SparApplication class because then we cannot create a servlet context

/*	@Inject
	void setHelloMessage(@Named("hello-message") final String helloMessage) {
		this.helloMessage = helloMessage;
	}*/

	@Inject
	void setUserController(final UserController userController) {
		appControllers.add(userController);
	}

	@Inject
    void setTransactionController(final TransactionController transactionController) {
        appControllers.add(transactionController);
    }
	
	@Inject
	void setRequestInfoController(final RequestInfoController requestInfoController) {
		//appControllers.add(requestInfoController);
	}

	@Inject
	void setErrorController(final ErrorController errorController) {
		appControllers.add(errorController);
	}

	@Override
	public void init() {
		appControllers.forEach(SparkController::init);

		get("/", (req, res) -> new MessageDto(helloMessage), json());

		after((req, res) -> res.type("application/json"));
	}
}
