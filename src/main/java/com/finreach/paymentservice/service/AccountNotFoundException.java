package com.finreach.paymentservice.service;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2762432403831706276L;

	public AccountNotFoundException(String id) {
		super("Account not found [id="+(id != null ? id.trim() : "")+"]");
	}
	
}
