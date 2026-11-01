package com.semicolon.safebank.model;

import com.semicolon.safebank.exception.InsufficientFundsException;
import com.semicolon.safebank.exception.InvalidAmountException;
import com.semicolon.safebank.exception.WithdrawalLimitExceededException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String accountHolderName;
    private BigDecimal balance;
    private final List<String> transactionHistory;
    private static final BigDecimal WITHDRAWAL_LIMIT = new BigDecimal("100000.00");

    public Account(String accountHolderName, BigDecimal openingBalance) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be blank.");
        }
        if (openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }

        this.accountHolderName = accountHolderName.trim();
        this.balance = openingBalance;
        this.transactionHistory = new ArrayList<>();

        if (openingBalance.compareTo(BigDecimal.ZERO) > 0) {
            transactionHistory.add("Opening Balance: +" + openingBalance);
        }
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public void deposit(BigDecimal amount) throws InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance = balance.add(amount);
        transactionHistory.add("Deposit: +" + amount);
    }

    public void withdraw(BigDecimal amount) throws InvalidAmountException, InsufficientFundsException, WithdrawalLimitExceededException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException("Insufficient funds. Available balance is: " + balance);
        }
        if (amount.compareTo(WITHDRAWAL_LIMIT) > 0) {
            throw new WithdrawalLimitExceededException("Withdrawal exceeds the single-withdrawal limit of 100,000.");
        }
        balance = balance.subtract(amount);
        transactionHistory.add("Withdrawal: -" + amount);
    }
}
