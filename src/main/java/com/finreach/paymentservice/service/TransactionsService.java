package com.finreach.paymentservice.service;

import com.finreach.paymentservice.model.Transaction;

public interface TransactionsService {

	Transaction create(String account, Double amount);

}