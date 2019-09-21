package com.finreach.paymentservice.store;

import java.util.List;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.domain.Payment;

public interface PaymentsService {

	Payment create(CreatePayment request) throws InvalidPaymentException;

	Payment get(String id) throws PaymentNotFoundException;

	List<Payment> all();

	Payment execute(String id) throws PaymentNotFoundException, InvalidPaymentException;

	Payment cancel(String id) throws PaymentNotFoundException, InvalidPaymentException;

}