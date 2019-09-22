package com.finreach.paymentservice.service;

public class InvalidPaymentException extends RuntimeException {

	private static final long serialVersionUID = 5335647589203008908L;

	public InvalidPaymentException() {
		super("Invalid payment");
	}
	
}
