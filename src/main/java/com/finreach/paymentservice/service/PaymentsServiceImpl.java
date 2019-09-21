package com.finreach.paymentservice.service;

import com.finreach.paymentservice.api.request.CreatePayment;
import com.finreach.paymentservice.model.Payment;
import com.finreach.paymentservice.model.PaymentBuilder;
import com.finreach.paymentservice.model.PaymentState;
import com.finreach.paymentservice.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsServiceImpl implements PaymentsService {

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private PaymentRepository paymentRepository;

    @Override
	public Payment create(CreatePayment request) throws AccountNotFoundException, InvalidPaymentException  {

    	if (!this.accountsService.exists(request.getSourceAccountId()))
        	throw new AccountNotFoundException();

    	if (!request.getDestinationAccountId().equals(request.getSourceAccountId()) && !this.accountsService.exists(request.getDestinationAccountId()))
        	throw new AccountNotFoundException();

        final Payment payment = new PaymentBuilder()
        		.setAmount( request.getAmount() )
        		.setSourceAccountId( request.getSourceAccountId() )
        		.setDestinationAccountId( request.getDestinationAccountId() )
        		.build();
        
        paymentRepository.save(payment);
        
        return payment;
    }

    @Override
	public Payment get(String id) throws PaymentNotFoundException {
  
    	Optional<Payment> opt = this.paymentRepository.findById(id);
        if (!opt.isPresent())
            throw new PaymentNotFoundException();
    	
        return opt.get();
    }

    @Override
	public List<Payment> all() {
    	return this.paymentRepository.findAll();
    }

    @Override
	public Payment execute(String id) throws PaymentNotFoundException, InvalidPaymentException {

    	Payment payment = get(id);
        if (!payment.getState().equals(PaymentState.CREATED))
            throw new InvalidPaymentException();

        try {
			this.accountsService.transfer(payment.getSourceAccountId(), payment.getDestinationAccountId(), payment.getAmount());
			payment.setState(PaymentState.EXECUTED);
		} catch (AccountNotFoundException | InsufficientFundsException e) {
			payment.setState(PaymentState.REJECTED);
		} finally {
			this.paymentRepository.save(payment);
		}

		return payment;
	}

    @Override
	public Payment cancel(String id) throws PaymentNotFoundException, InvalidPaymentException {

        Payment payment = get(id);
        if (!payment.getState().equals(PaymentState.CREATED))
            throw new InvalidPaymentException();
    	
    	payment.setState(PaymentState.CANCELED);

    	this.paymentRepository.save(payment);
        return payment;
    }

}
