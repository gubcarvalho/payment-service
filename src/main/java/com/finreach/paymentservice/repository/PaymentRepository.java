package com.finreach.paymentservice.repository;

import org.springframework.stereotype.Repository;

import com.finreach.paymentservice.model.Payment;

@Repository
public class PaymentRepository extends GenericMapRepository<Payment, String> {
	
	public void save(Payment payment) {
    	if (payment != null)
    		save(payment.getId(), payment);
	}

}
