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

public class Accounts {

    private static final Map<String, Account> ACCOUNTS = new HashMap<>();

    public static Account create(CreateAccount request) {
    	
    	final Account account = new AccountBuilder()
    		.setBalance(request.getBalance())
    		.build();
    	
        ACCOUNTS.put(account.getId(), account);
        return account;
    }
    
    public static boolean exists(String accountId) {
    	final Optional<Account> accountOpt = get(accountId);
        if (!accountOpt.isPresent())
            return false;
        return true;
    }
    
    public static boolean checkWithdrawn(String id, Double amount) {

    	final Optional<Account> accountOpt = get(id);
        if (!accountOpt.isPresent())
            return false;
    	
        final Account account = accountOpt.get();
        return (account.getBalance() >= amount);
    }
    
    public static void transaction(String id, Double amount) {

    	final Optional<Account> accountOpt = get(id);
        if (!accountOpt.isPresent())
            return;

        Account account = accountOpt.get();
        account.getTransactions().add(Transactions.create(id, amount));
        account.setBalance(Double.sum(account.getBalance(), amount));
        
        ACCOUNTS.put(id, account);
    }

    public static Optional<Account> get(String id) {
        return Optional.ofNullable(ACCOUNTS.get(id));
    }

    public static List<Account> all() {
    	return ACCOUNTS.values().stream()
    			.sorted(Comparator.comparing(Account::getId))
    			.collect(Collectors.toList());
    }

}
