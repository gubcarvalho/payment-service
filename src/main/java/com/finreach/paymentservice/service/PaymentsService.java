package com.finreach.paymentservice.service;

import java.util.List;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.model.Payment;

public interface PaymentsService {

	Payment create(CreatePayment request);

	Payment get(String id);

	List<Payment> all();

	Payment execute(String id);

	Payment cancel(String id);

}