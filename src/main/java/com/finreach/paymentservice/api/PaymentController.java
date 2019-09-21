package com.finreach.paymentservice.api;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.model.Payment;
import com.finreach.paymentservice.service.AccountNotFoundException;
import com.finreach.paymentservice.service.InvalidPaymentException;
import com.finreach.paymentservice.service.PaymentNotFoundException;
import com.finreach.paymentservice.service.PaymentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
	
	@Autowired
	private PaymentsService paymentsService;
	
    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody CreatePayment request) {
    	try {
    		final Payment payment = this.paymentsService.create(request);
        	return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    	} catch (AccountNotFoundException ex) {
        	return ResponseEntity.notFound().build();
    	} catch (InvalidPaymentException ex) {
        	return ResponseEntity.badRequest().build();
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

    @PatchMapping(path = "/{id}")
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
    
    @DeleteMapping(path = "/{id}")
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
}
