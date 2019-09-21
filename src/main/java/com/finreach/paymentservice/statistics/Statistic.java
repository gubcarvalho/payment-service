package com.finreach.paymentservice.statistics;

import java.util.List;

import com.finreach.paymentservice.model.Transaction;

public class Statistic {

    private String accountId;
    private Double maxTrans;
    private Double minTrans;
    private Double avgTrans;
    private List<Transaction> transactions;

    protected Statistic() {
    	super();
    }
    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getMaxTrans() {
        return maxTrans;
    }

    public void setMaxTrans(Double maxTrans) {
        this.maxTrans = maxTrans;
    }

    public Double getMinTrans() {
        return minTrans;
    }

    public void setMinTrans(Double minTrans) {
        this.minTrans = minTrans;
    }

    public Double getAvgTrans() {
        return avgTrans;
    }

    public void setAvgTrans(Double avgTrans) {
        this.avgTrans = avgTrans;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}