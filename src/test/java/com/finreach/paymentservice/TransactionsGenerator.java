package com.finreach.paymentservice;

import com.finreach.paymentservice.api.request.CreateAccount;
import com.finreach.paymentservice.model.Account;
import com.finreach.paymentservice.model.Transaction;
import com.finreach.paymentservice.service.AccountNotFoundException;
import com.finreach.paymentservice.service.AccountsService;
import com.finreach.paymentservice.service.InsufficientFundsException;
import com.finreach.paymentservice.statistics.StatisticBuilder;
import com.finreach.paymentservice.statistics.Statistics;
import com.finreach.paymentservice.util.MathUtils;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TransactionsGenerator {
    private static final int ACCOUNTS = 100;
    private static final int DURATION = 10;
    private static final double MIN_AMOUNT = 10d;
    private static final double MAX_AMOUNT = 1000d;
    private static final double BALANCE = 1000000d;

    @Autowired
    private AccountsService accountsService;
    
    private List<String> genAccounts = new ArrayList<>();

    public void generate() throws InterruptedException {
        generateAccounts();

        long start = System.currentTimeMillis();
        long later = start + TimeUnit.SECONDS.toMillis(DURATION);
        while (start < later) {
            start = System.currentTimeMillis();
            Thread.sleep(10);

            final RandomAccounts randomAccounts = randomAccounts();
            final double amount = randomAmount();
            
            try {
				this.accountsService.transfer(randomAccounts.a1, randomAccounts.a2, amount);
			} catch (AccountNotFoundException | InsufficientFundsException e) {
				// ignore exceptions
			}
        }
    }

    public Statistics calculate(int periodInSeconds) {
        Date when = Date.from(Instant.now().minusSeconds(periodInSeconds));
        final Statistics statistics = new Statistics();
        this.accountsService.all()
                .forEach(account -> calculate(periodInSeconds, when, statistics, account));

        return statistics;
    }

    private void calculate(int periodInSeconds, Date when, Statistics statistics, Account account) {

        List<Transaction> filteredTransactions = account.getTransactions()
                .stream()
                .filter(transaction -> within(transaction.getCreated(), when, periodInSeconds))
                .filter(transaction -> transaction.getAmount() > 0)
                .collect(Collectors.toList());

        if (filteredTransactions.isEmpty())
            return;
        
        statistics.add(new StatisticBuilder()
    				.setAccountId(account.getId())
    				.setTransactions(filteredTransactions)
    				.build());
    }

    private void generateAccounts() {
        for (int i = 0; i < ACCOUNTS; i++) {
            genAccounts.add(this.accountsService.create(new CreateAccount(BALANCE)).getId());
        }
    }

    private double randomAmount() {
        return MathUtils.round(RandomUtils.nextDouble(MIN_AMOUNT, MAX_AMOUNT));
    }

    private RandomAccounts randomAccounts() {
        final int size = genAccounts.size();
        final String a1 = genAccounts.get(RandomUtils.nextInt(0, size));
        String a2 = genAccounts.get(RandomUtils.nextInt(0, size));
        while (a1.equals(a2)) {
            a2 = genAccounts.get(RandomUtils.nextInt(0, size));
        }

        return new RandomAccounts(a1, a2);
    }

    private class RandomAccounts {
        String a1;
        String a2;

        RandomAccounts(String a1, String a2) {
            this.a1 = a1;
            this.a2 = a2;
        }
    }

    private boolean within(Date d1, Date d2, int seconds) {
        if (d2 == null) {
            return true;
        }

        final long diff = d2.toInstant().getEpochSecond() - d1.toInstant().getEpochSecond();
        return diff >= 0 && diff < seconds;
    }
}
