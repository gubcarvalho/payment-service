package com.finreach.paymentservice.domain;

import com.finreach.paymentservice.store.InvalidPaymentException;

public class PaymentBuilder {

	private Double amount;
    
	private String sourceAccountId;
    
	private String destinationAccountId;

	public PaymentBuilder setAmount(Double amount) {
		this.amount = (amount != null ? amount : 0d);
		return this;
	}

	public PaymentBuilder setSourceAccountId(String sourceAccountId) {
		this.sourceAccountId = (sourceAccountId != null ? sourceAccountId : "");
		return this;
	}

	public PaymentBuilder setDestinationAccountId(String destinationAccountId) {
		this.destinationAccountId = (destinationAccountId != null ? destinationAccountId : "");
		return this;
	}

    public Payment build() throws InvalidPaymentException {

    	if (this.amount <= 0 || this.sourceAccountId.equals(this.destinationAccountId))
    		throw new InvalidPaymentException();
    	
    	Payment payment = new Payment();
    	
    	final String id = String.valueOf(System.nanoTime());
    	
    	payment.setId(id);
    	payment.setAmount(this.amount);;
    	payment.setSourceAccountId(this.sourceAccountId);
    	payment.setDestinationAccountId(this.destinationAccountId);
    	payment.setState(PaymentState.CREATED);
    	
    	return payment;
    }

}
