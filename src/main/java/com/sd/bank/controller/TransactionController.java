package com.sd.bank.controller;

import static com.sd.bank.dto.JsonUtil.fromJson;
import static com.sd.bank.dto.JsonUtil.json;
import static spark.Spark.post;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.sd.bank.dto.TransferRequest;
import com.sd.bank.service.AccountService;

/**
 * Handles all Transaction related requests.
 */
@Singleton
public class TransactionController implements SparkController {

  private final AccountService accountService;

  @Inject
  public TransactionController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public void init() {
    post("/transfer", (req, res) -> {
      final String body = req.body();
      final TransferRequest transferRequest = fromJson(body, TransferRequest.class);
      return accountService.transfer(transferRequest);
    }, json());

  }
}
