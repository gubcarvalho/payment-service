package com.finreach.paymentservice.domain;

public class AccountBuilder {

	private Double balance = 0d;
    
	public AccountBuilder setBalance(Double balance) {
		this.balance = (balance != null ? balance : 0d);
		return this;
	}

    public Account build() {

    	Account account = new Account();
    	
    	final String id = String.valueOf(System.nanoTime());
    	
    	account.setId(id);
    	account.setBalance(this.balance);

    	return account;
    }

}
