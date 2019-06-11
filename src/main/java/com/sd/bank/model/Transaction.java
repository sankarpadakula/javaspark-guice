package com.sd.bank.model;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
  public enum TransactionType {
    IN, OUT
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private long account_id;

  private double amount;

  private TransactionType type;

  @Column(nullable = false)
  private String message;

  @Column(nullable = false)
  private Date issuedAt;

  public Optional<String> getMessage() {
    return Optional.ofNullable(message);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getAccount() {
    return account_id;
  }

  public void setAccount(long account) {
    this.account_id = account;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public Date getIssuedAt() {
    return issuedAt;
  }

  public void setIssuedAt(Date issuedAt) {
    this.issuedAt = issuedAt;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
