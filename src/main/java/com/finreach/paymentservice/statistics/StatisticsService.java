package com.finreach.paymentservice.statistics;

import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.domain.Transaction;
import com.finreach.paymentservice.statistics.StatisticBuilder;
import com.finreach.paymentservice.statistics.Statistics;
import com.finreach.paymentservice.store.Accounts;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StatisticsService {

    public static Statistics generateStatistics(Date from, Integer seconds) {
       	
    	final Statistics statistics = new Statistics();
    	
    	Predicate<Transaction> p = t -> t.isEligibleForEstatistics(from, seconds);
    	Accounts.all()
    		.forEach(account -> calculateEstatistics(account, p, statistics));

    	return statistics;
    }

	private static void calculateEstatistics(Account account, Predicate<Transaction> p, Statistics statistics) {
        
		List<Transaction> filtered = account.getTransactions()
                .stream()
                .filter(p)
                .collect(Collectors.toList());

        if (filtered.isEmpty())
            return;
        
        statistics.add(new StatisticBuilder()
    				.setAccountId(account.getId())
    				.setTransactions(filtered)
    				.build());
	}
    
}
