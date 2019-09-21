package com.finreach.paymentservice.api;

import com.finreach.paymentservice.api.request.CreateAccount;
import com.finreach.paymentservice.model.Account;
import com.finreach.paymentservice.service.AccountNotFoundException;
import com.finreach.paymentservice.service.AccountsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

	@Autowired
	private AccountsService accountsService;
	
    @PostMapping
    public ResponseEntity<Account> create(@RequestBody CreateAccount request) {
        final Account account = this.accountsService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Account> get(@PathVariable("id") String id) {
    	try {
		    return ResponseEntity.ok(this.accountsService.get(id));
		} catch (AccountNotFoundException e) {
	    	return ResponseEntity.notFound().build();
		}
    }

}
