package com.finreach.paymentservice.service;

public class InsufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = -5419846323887239871L;

	public InsufficientFundsException() {
		super("The account doesn't have enough balance");
	}
	
}
