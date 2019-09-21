package com.finreach.paymentservice.statistics;

import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.domain.Transaction;
import com.finreach.paymentservice.statistics.StatisticBuilder;
import com.finreach.paymentservice.statistics.Statistics;
import com.finreach.paymentservice.store.AccountsService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private AccountsService accountsService;
	
	private int maxSeconds = 10;
	
    @Override
	public Statistics generateStatistics(Integer second) {
       	
    	final Statistics statistics = new Statistics();
    	
    	final int sec = (second > this.maxSeconds ? this.maxSeconds : second);
       	Date from = Date.from( Instant.now().minusSeconds( sec ));
    	
    	Predicate<Transaction> p = t -> t.isEligibleForEstatistics(from, sec);
    	this.accountsService.all()
    		.forEach(account -> calculateEstatistics(account, p, statistics));

    	return statistics;
    }

	private void calculateEstatistics(Account account, Predicate<Transaction> p, Statistics statistics) {
        
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
