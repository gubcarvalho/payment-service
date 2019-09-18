package com.finreach.paymentservice.store;

public class InvalidPaymentException extends Exception {

	private static final long serialVersionUID = 9203764807332383536L;

	public InvalidPaymentException() {
		super("Invalid payment");
	}
	
}
