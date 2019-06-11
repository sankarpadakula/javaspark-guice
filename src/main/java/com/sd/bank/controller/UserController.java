package com.sd.bank.controller;

import static com.sd.bank.dto.JsonUtil.fromJson;
import static com.sd.bank.dto.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.post;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.sd.bank.model.Account;
import com.sd.bank.service.AccountService;

/**
 * Handles all Account related requests.
 */
@Singleton
public class UserController implements SparkController {

  private final AccountService accountService;

  @Inject
  public UserController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public void init() {
    get("/accounts", (req, res) -> {
      final String sortcode = req.queryParams("sortcode");
      final String accountNum = req.queryParams("account");
      if (sortcode != null && accountNum != null) {
        Account account = accountService.getAccount(sortcode, accountNum);
        if (account == null) {
          res.status(404);
          return String.format("Account '%s' was not found", accountNum);
        }
        return account;
      } else {
        return accountService.getAccounts();
      }
    }, json());

    post("/account", (req, res) -> {
      final String body = req.body();
      final Account account = fromJson(body, Account.class);
      return accountService.createAccount(account);
    }, json());
  }
}
