package com.finreach.paymentservice.api.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.finreach.paymentservice.service.AccountNotFoundException;
import com.finreach.paymentservice.service.InsufficientFundsException;
import com.finreach.paymentservice.service.InvalidPaymentException;
import com.finreach.paymentservice.service.PaymentNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<RestApiError> handleAccountNotFoundException(AccountNotFoundException e) {
    	return new ResponseEntity<RestApiError>(getError(e), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<RestApiError> handlePaymentNotFoundException(PaymentNotFoundException e) {
    	return new ResponseEntity<RestApiError>(getError(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<RestApiError> handleInvalidPaymentException(InvalidPaymentException e) {
    	return ResponseEntity.badRequest().body(getError(e));
    }
    
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<RestApiError> handleInsufficientFundsException(InsufficientFundsException e) {
    	return ResponseEntity.badRequest().body(getError(e));
    }
    
    private RestApiError getError(Throwable ex) {
    	return new RestApiError(ex);
    }
}
