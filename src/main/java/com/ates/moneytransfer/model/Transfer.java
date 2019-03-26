package com.ates.moneytransfer.model;


import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Transfer {

	
	private static final AtomicInteger COUNTER = new AtomicInteger();

    private int id;

    private int toAccountId;
    
    private int fromAccountId;

    private BigDecimal amount;

    private String comment;

    private TransferStatus status;

    public Transfer(int id, int toAccountId, int fromAccountId, BigDecimal amount, String comment) {
        this.id = COUNTER.getAndIncrement();
        this.toAccountId = toAccountId;
        this.fromAccountId = fromAccountId;
        this.comment = comment;
        this.amount=amount;
        this.status=TransferStatus.CREATED;
    }

    public Transfer() {
        this.id = COUNTER.getAndIncrement();
        this.status=TransferStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


    public enum TransferStatus {
        CREATED,
        UPDATED,
        AMOUNTZERO,
        AMOUNTLESS,
        TOIDNULL,
        FROMIDNULL
    }
}
