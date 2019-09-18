package com.finreach.paymentservice.store;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = 272413565572752930L;

	public InsufficientFundsException() {
		super("The account doesn't have enough balance");
	}
	
}
