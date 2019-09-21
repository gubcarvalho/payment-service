package com.finreach.paymentservice.service;

import java.util.List;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.model.Payment;

public interface PaymentsService {

	Payment create(CreatePayment request) throws AccountNotFoundException, InvalidPaymentException;

	Payment get(String id) throws PaymentNotFoundException;

	List<Payment> all();

	Payment execute(String id) throws PaymentNotFoundException, InvalidPaymentException;

	Payment cancel(String id) throws PaymentNotFoundException, InvalidPaymentException;

}