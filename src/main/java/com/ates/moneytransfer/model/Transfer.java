package com.ates.moneytransfer.model;


import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Transfer {

	
	private static final AtomicInteger COUNTER = new AtomicInteger();

    private int id;

    private int toAccountId;
    
    private int fromAccountId;

    private BigDecimal amount;

    private String comment;

    private TransferStatus status;

    public Transfer(int toAccountId, int fromAccountId, BigDecimal amount, String comment) {
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




    public enum TransferStatus {
        CREATED{
            @Override
            public String toString() {
                return "Created";
            }
        },

        UPDATED{
            @Override
            public String toString() {
                return "Updated";
            }
        },

        AMOUNTZERO{
            @Override
            public String toString() {
                return "Because transfer amount is zero, process can not be proceed. Please check your information!";
            }
        },

        AMOUNTLESS{
            @Override
            public String toString() {
                return "Balance is less than amount you want send. Please check your information!";
            }
        },

        TOIDNULL{
            @Override
            public String toString() {
                return "sender id can not be null. Please check your information!";
            }
        },

        FROMIDNULL{
            @Override
            public String toString() {
                return "id that you send money can not be null. Please check your information!";
            }
        }
    }
}
