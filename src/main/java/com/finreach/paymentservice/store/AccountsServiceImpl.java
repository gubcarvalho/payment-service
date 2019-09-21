package com.finreach.paymentservice.store;

import com.finreach.paymentservice.api.request.CreateAccount;
import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.domain.AccountBuilder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsServiceImpl implements AccountsService {

    private static final Map<String, Account> ACCOUNTS = new HashMap<>();

    @Autowired
    private TransactionsService transactionsService;
    
    @Override
	public Account create(CreateAccount request) {
    	
    	final Account account = new AccountBuilder()
    		.setBalance(request.getBalance())
    		.build();
    	
        ACCOUNTS.put(account.getId(), account);
        return account;
    }
    
    @Override
	public boolean exists(String accountId) {
    	final Optional<Account> accountOpt = get(accountId);
        if (!accountOpt.isPresent())
            return false;
        return true;
    }
    
    @Override
	public boolean checkWithdrawn(String id, Double amount) {

    	final Optional<Account> accountOpt = get(id);
        if (!accountOpt.isPresent())
            return false;
    	
        final Account account = accountOpt.get();
        return (account.getBalance() >= amount);
    }
    
    @Override
	public void transaction(String id, Double amount) {

    	final Optional<Account> accountOpt = get(id);
        if (!accountOpt.isPresent())
            return;

        Account account = accountOpt.get();
        account.getTransactions().add(this.transactionsService.create(id, amount));
        account.setBalance(Double.sum(account.getBalance(), amount));
        
        ACCOUNTS.put(id, account);
    }

    @Override
	public Optional<Account> get(String id) {
        return Optional.ofNullable(ACCOUNTS.get(id));
    }

    @Override
	public List<Account> all() {
    	return ACCOUNTS.values().stream()
    			.sorted(Comparator.comparing(Account::getId))
    			.collect(Collectors.toList());
    }

}
