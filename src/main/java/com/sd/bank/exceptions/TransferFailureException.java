package com.sd.bank.exceptions;

public class TransferFailureException extends RuntimeException {
  public TransferFailureException(String fromNotFound) {
    errorCode = fromNotFound;
  }

  String errorCode;

  public String getErrorCode() {
    return errorCode;
  }

}
