package com.finreach.paymentservice.service;

import com.finreach.paymentservice.model.Transaction;
import com.finreach.paymentservice.model.TransactionBuilder;
import com.finreach.paymentservice.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Override
	public Transaction create(String account, Double amount) {

        final Transaction transaction = new TransactionBuilder()
        		.setAccount(account)
        		.setAmount(amount)
        		.build();
        
        transactionRepository.save(transaction);

        return transaction;
    }

}
