package com.finreach.paymentservice.statistics;

import com.finreach.paymentservice.model.Transaction;
import com.finreach.paymentservice.util.MathUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticBuilder {

    private String accountId;
 
    private List<Transaction> transactions;

    public StatisticBuilder setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public StatisticBuilder setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }
    
    public Statistic build() {
   
    	double min = Double.MAX_VALUE, max = 0d, sum = 0d;
        for (Transaction transaction : this.transactions) {
            min = min < transaction.getAmount() ? min : transaction.getAmount();
            max = max > transaction.getAmount() ? max : transaction.getAmount();
            sum += transaction.getAmount();
        }
    	
    	Statistic statistic = new Statistic();

    	statistic.setAccountId(this.accountId);
    	statistic.setMinTrans(min);
    	statistic.setMaxTrans(max);
    	if (this.transactions.size() > 0)
    		statistic.setAvgTrans(MathUtils.round(sum / this.transactions.size()));

    	statistic.setTransactions(this.transactions.stream()
    			.sorted(Comparator.comparing(Transaction::getCreated))
    			.collect(Collectors.toList()));
    	
    	return statistic;
    }
}