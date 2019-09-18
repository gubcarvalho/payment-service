package com.finreach.paymentservice.store;

import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.domain.PaymentBuilder;
import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.domain.PaymentState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Payments {

    private static final Map<String, Payment> PAYMENTS = new HashMap<>();

    public static Payment create(CreatePayment request) throws InvalidPaymentException {

        final Payment payment = new PaymentBuilder()
        		.setAmount( request.getAmount() )
        		.setSourceAccountId( request.getSourceAccountId() )
        		.setDestinationAccountId( request.getDestinationAccountId() )
        		.build();
        
        PAYMENTS.put(payment.getId(), payment);
        
        return payment;
    }

    public static Payment get(String id) throws PaymentNotFoundException {
    	
    	Optional<Payment> opt = Optional.ofNullable(PAYMENTS.get(id));
        if (!opt.isPresent())
            throw new PaymentNotFoundException();
    	
        return opt.get();
    }

    public static List<Payment> all() {
        return new ArrayList<>(PAYMENTS.values());
    }

    public static Payment execute(String id) throws PaymentNotFoundException, InvalidPaymentException {

    	Payment payment = get(id);
        if (!payment.getState().equals(PaymentState.CREATED))
            throw new InvalidPaymentException();

		if (!Accounts.checkWithdrawn(payment.getSourceAccountId(), payment.getAmount())) {
			// no funds -> reject the payment
			payment.setState(PaymentState.REJECTED);
		} else {
			// A transaction should be created on the source account with a negative amount.
			Accounts.transaction(payment.getSourceAccountId(), payment.getAmount()*-1);
			
			// A transaction should be created on the destination account with a positive amount.
			Accounts.transaction(payment.getDestinationAccountId(), payment.getAmount());

			// The state must be changed to EXECUTED.
			payment.setState(PaymentState.EXECUTED);
		}
		PAYMENTS.put(payment.getId(), payment);
		
		return payment;
	}

    public static Payment cancel(String id) throws PaymentNotFoundException, InvalidPaymentException {

        Payment payment = get(id);
        if (!payment.getState().equals(PaymentState.CREATED))
            throw new InvalidPaymentException();
    	
    	payment.setState(PaymentState.CANCELED);
        PAYMENTS.put(payment.getId(), payment);
        return payment;
    }

}
