package com.finreach.paymentservice.repository;

import org.springframework.stereotype.Repository;

import com.finreach.paymentservice.model.Account;

@Repository
public class AccountRepository extends GenericMapRepository<Account, String> {

    public void save(Account account) {
    	if (account != null)
    		save(account.getId(), account);
    }

}
