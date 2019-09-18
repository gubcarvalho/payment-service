package com.finreach.paymentservice.store;

public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = 272413565572752930L;

	public AccountNotFoundException() {
		super("The account doesn't have enough balance");
	}
	
}
