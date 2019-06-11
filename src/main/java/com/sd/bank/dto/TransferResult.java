package com.sd.bank.dto;

public class TransferResult {

  public boolean isTransferred() {
    return transferred;
  }

  public void setTransferred(boolean transferred) {
    this.transferred = transferred;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public TransferResult(boolean b, String error) {
    transferred = b;
    errorCode = error;
  }

  public static TransferResult fail(String errorCode) {
    return new TransferResult(false, errorCode);
  }

  public static TransferResult success() {
    return new TransferResult(true, null);
  }

  boolean transferred;
  String errorCode;

}
