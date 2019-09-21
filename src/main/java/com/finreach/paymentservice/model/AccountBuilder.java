package com.finreach.paymentservice.model;

public class AccountBuilder {

	private Double balance;
    
	public AccountBuilder setBalance(Double balance) {
		this.balance = balance;
		return this;
	}

    public Account build() {

    	Account account = new Account();
    	
    	account.setId(String.valueOf(System.nanoTime()));
    	account.setBalance((this.balance != null ? this.balance : 0d));

    	return account;
    }

}
