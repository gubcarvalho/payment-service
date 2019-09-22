package com.finreach.paymentservice.service;

public class PaymentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 631016813929957976L;

	public PaymentNotFoundException() {
		super("Payment not found");
	}
	
}
