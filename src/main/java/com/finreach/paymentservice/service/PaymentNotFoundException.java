package com.finreach.paymentservice.service;

public class PaymentNotFoundException extends Exception {

	private static final long serialVersionUID = -1675817713846745807L;

	public PaymentNotFoundException() {
		super("Payment not found");
	}
	
}
