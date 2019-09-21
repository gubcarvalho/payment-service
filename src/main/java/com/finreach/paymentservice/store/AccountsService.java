package com.finreach.paymentservice.store;

import java.util.List;
import java.util.Optional;

import com.finreach.paymentservice.api.request.CreateAccount;
import com.finreach.paymentservice.domain.Account;

public interface AccountsService {

	Account create(CreateAccount request);

	boolean exists(String accountId);

	boolean checkWithdrawn(String id, Double amount);

	void transaction(String id, Double amount);

	Optional<Account> get(String id);

	List<Account> all();

}