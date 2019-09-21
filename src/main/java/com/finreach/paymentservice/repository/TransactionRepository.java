package com.finreach.paymentservice.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.finreach.paymentservice.model.Transaction;

@Repository
public class TransactionRepository {

    private final Map<String, Transaction> transactions = new HashMap<>();
	
    public void save(Transaction transaction) {
    	if (transaction == null)
    		return;

    	this.transactions.put(transaction.getId(), transaction);
    }
    
	public boolean exists(String id) {
		return findById(id).isPresent();
    }

	public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(this.transactions.get(id));
    }

	public List<Transaction> findAll() {
    	return transactions.values().stream()
    			.collect(Collectors.toList());
    }
	
}
