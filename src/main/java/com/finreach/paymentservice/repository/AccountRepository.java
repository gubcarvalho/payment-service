package com.finreach.paymentservice.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.finreach.paymentservice.model.Account;

@Repository
public class AccountRepository {

	private final Map<String, Account> accounts = new HashMap<>();
	
    public void save(Account account) {
    	if (account == null)
    		return;
    	this.accounts.put(account.getId(), account);
    }
    
	public boolean exists(String id) {
		return findById(id).isPresent();
    }

	public Optional<Account> findById(String id) {
        return Optional.ofNullable(this.accounts.get(id));
    }

	public List<Account> findAll() {
    	return this.accounts.values().stream()
    			.collect(Collectors.toList());
    }

}
