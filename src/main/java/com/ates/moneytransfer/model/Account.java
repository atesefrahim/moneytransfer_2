package com.ates.moneytransfer.model;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {
	
	private static final AtomicInteger COUNTER = new AtomicInteger();

    private int accountId;

    private String name;

    private BigDecimal balance;

    public Account(int accountId, String name, BigDecimal balance) {
        this.accountId = COUNTER.getAndIncrement();
        this.name = name;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }
    

    public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void withdraw(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Account() {

        this.accountId = COUNTER.getAndIncrement();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return accountId;
    }
}