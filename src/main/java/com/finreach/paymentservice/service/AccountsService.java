package com.finreach.paymentservice.service;

import java.util.List;

import com.finreach.paymentservice.api.request.CreateAccount;
import com.finreach.paymentservice.model.Account;

public interface AccountsService {

	Account create(CreateAccount request);

	boolean exists(String accountId);

	Account get(String id);

	List<Account> all();
	
	void transfer(String sourceAccountId, String destinationAccountId, Double amount);

}