package com.finreach.paymentservice.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.finreach.paymentservice.model.Payment;

@Repository
public class PaymentRepository {

	private final Map<String, Payment> payments = new HashMap<>();
	
    public void save(Payment payment) {
    	if (payment == null)
    		return;

    	this.payments.put(payment.getId(), payment);
    }
    
	public boolean exists(String id) {
		return findById(id).isPresent();
    }

	public Optional<Payment> findById(String id) {
        return Optional.ofNullable(this.payments.get(id));
    }

	public List<Payment> findAll() {
    	return this.payments.values().stream()
    			.collect(Collectors.toList());
    }

}
