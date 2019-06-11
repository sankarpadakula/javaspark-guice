package com.sd.bank.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String sortCode;

  @Column(nullable = false)
  private String accountNumber;

  @Column(nullable = false)
  private Date openDate;

  @Column(nullable = false)
  private double balance;

  public boolean hasAtLeast(double amount) {
    return balance >= amount;
  }

  public void debit(double amount) {
    balance = balance - amount;
  }

  public void credit(double amount) {
    balance = balance + amount;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSortCode() {
    return sortCode;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public Date getOpenDate() {
    return openDate;
  }

  public void setOpenDate(Date openDate) {
    this.openDate = openDate;
  }

  public void setSortCode(String sortCode) {
    this.sortCode = sortCode;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

}
