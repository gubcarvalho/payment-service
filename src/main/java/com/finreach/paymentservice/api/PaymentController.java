package com.finreach.paymentservice.api;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.model.Payment;
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
		final Payment payment = this.paymentsService.create(request);
    	return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Payment> get(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(this.paymentsService.get(id));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Payment> execute(@PathVariable("id") String id) {
		final Payment payment = this.paymentsService.execute(id);
        return ResponseEntity.ok().body(payment);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Payment> cancel(@PathVariable("id") String id) {
		final Payment payment = this.paymentsService.cancel(id);
        return ResponseEntity.ok().body(payment);
    }
    
}
