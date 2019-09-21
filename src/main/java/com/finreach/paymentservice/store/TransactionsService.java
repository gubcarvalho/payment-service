package com.finreach.paymentservice.store;

import com.finreach.paymentservice.domain.Transaction;

public interface TransactionsService {

	Transaction create(String account, Double amount);

}