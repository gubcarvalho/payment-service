package com.finreach.paymentservice.api;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.store.AccountsService;
import com.finreach.paymentservice.store.InvalidPaymentException;
import com.finreach.paymentservice.store.PaymentNotFoundException;
import com.finreach.paymentservice.store.PaymentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private PaymentsService paymentsService;
	
    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody CreatePayment request) {

    	// validar contas
    	if (!accountValidate(request))
        	return ResponseEntity.notFound().build();
        	
    	try {
    		final Payment payment = this.paymentsService.create(request);
        	return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    	} catch (InvalidPaymentException ex) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Payment> get(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok().body(this.paymentsService.get(id));
		} catch (PaymentNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
    }

    @PutMapping(path = "/execute/{id}")
    public ResponseEntity<Payment> execute(@PathVariable("id") String id) {

    	try {
			final Payment payment = this.paymentsService.execute(id);
	        return ResponseEntity.ok().body(payment);
		} catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
		} catch (InvalidPaymentException e) {
        	return ResponseEntity.badRequest().build();
		}

    }
    
    @PutMapping(path = "/cancel/{id}")
    public ResponseEntity<Payment> cancel(@PathVariable("id") String id) {
    	try {
			final Payment payment = this.paymentsService.cancel(id);
	        return ResponseEntity.ok().body(payment);
		} catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
		} catch (InvalidPaymentException e) {
        	return ResponseEntity.badRequest().build();
		}
    }
    
    private boolean accountValidate(final CreatePayment request) {
        
    	if (!this.accountsService.exists(request.getSourceAccountId()))
        	return false;

        if (request.getDestinationAccountId() != request.getSourceAccountId() && !this.accountsService.exists(request.getDestinationAccountId()))
        	return false;
        
    	return true;
    }
}
