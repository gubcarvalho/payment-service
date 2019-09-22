package com.finreach.paymentservice.service;

import com.finreach.paymentservice.api.request.CreateAccount;
import com.finreach.paymentservice.model.Account;
import com.finreach.paymentservice.model.AccountBuilder;
import com.finreach.paymentservice.model.Transaction;
import com.finreach.paymentservice.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsServiceImpl implements AccountsService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private TransactionsService transactionsService;
    
    @Override
	public Account create(CreateAccount request) {
    	
    	final Account account = new AccountBuilder()
    		.setBalance(request.getBalance())
    		.build();
    	
    	this.accountRepository.save(account);
        return account;
    }
    
    @Override
	public boolean exists(String accountId) {
    	return this.accountRepository.exists(accountId);
    }
    
    @Override
    public void transfer(String sourceAccountId, String destinationAccountId, Double amount) {
    	
    	// get source and check funds
    	Account sourceAccount = this.get(sourceAccountId);
    	if (sourceAccount.getBalance() < amount)
    		throw new InsufficientFundsException();
    				
    	// get destination
    	Account destinationAccount = this.get(destinationAccountId);

    	// transfer the money between accounts
		this.transaction(sourceAccount, -amount);
		this.transaction(destinationAccount, amount);
    }
    
	private void transaction(Account account, Double amount) {

		final Transaction transaction = this.transactionsService.create(account.getId(), amount);
		
		account.getTransactions().add(transaction);
        account.setBalance(Double.sum(account.getBalance(), transaction.getAmount()));
  
        this.accountRepository.save(account);
    }
    
    @Override
	public Account get(String id) {
    	
    	final Optional<Account> accountOpt = this.accountRepository.findById(id);
    	if (!accountOpt.isPresent())
    		throw new AccountNotFoundException(id);

    	return accountOpt.get();
    }

    @Override
	public List<Account> all() {
    	return this.accountRepository.findAll();
    }

}
