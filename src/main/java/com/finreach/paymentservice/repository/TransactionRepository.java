package com.finreach.paymentservice.repository;

import org.springframework.stereotype.Repository;

import com.finreach.paymentservice.model.Transaction;

@Repository
public class TransactionRepository extends GenericMapRepository<Transaction, String> {

    public void save(Transaction transaction) {
    	if (transaction != null)
    		save(transaction.getId(), transaction);
    }

}
