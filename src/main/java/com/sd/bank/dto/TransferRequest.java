package com.sd.bank.dto;

public final class TransferRequest {

  SortCodeAccountNumber from;

  SortCodeAccountNumber to;

  double amount;

  String message;

  public SortCodeAccountNumber getFrom() {
    return from;
  }

  public void setFrom(SortCodeAccountNumber from) {
    this.from = from;
  }

  public SortCodeAccountNumber getTo() {
    return to;
  }

  public void setTo(SortCodeAccountNumber to) {
    this.to = to;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}