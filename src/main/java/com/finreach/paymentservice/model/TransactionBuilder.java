package com.finreach.paymentservice.model;

import java.time.Instant;
import java.util.Date;

public class TransactionBuilder {

	private String account;
    
	private Double amount;
    
    public TransactionBuilder setAccount(String account) {
        this.account = (account == null ? "" : account);
        return this;
    }

    public TransactionBuilder setAmount(Double amount) {
        this.amount = (amount == null ? 0d : amount);
        return this;
    }

    public Transaction build() {

    	Transaction transaction = new Transaction();
    	transaction.setId(String.valueOf(System.nanoTime()));
    	transaction.setCreated(Date.from(Instant.now()));
    	transaction.setAccount(this.account);
    	transaction.setAmount(this.amount);
    	return transaction;

    }
}
