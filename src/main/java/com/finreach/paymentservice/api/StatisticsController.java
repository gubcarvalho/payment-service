package com.finreach.paymentservice.api;

import com.finreach.paymentservice.statistics.StatisticsService;
import com.finreach.paymentservice.statistics.Statistics;

import java.time.Instant;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
	
	private static final int MAX_SECONDS = 10;
	
    @GetMapping(path = "/{second}")
    public Statistics get(@PathVariable("second") Integer second) {
    	
    	second = (second > MAX_SECONDS ? MAX_SECONDS : second);
       	Date from = Date.from( Instant.now().minusSeconds( second ));
    	
    	return StatisticsService.generateStatistics(from, second);
    }

}
