package com.finreach.paymentservice.api;

import com.finreach.paymentservice.statistics.Statistics;
import com.finreach.paymentservice.statistics.StatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	
    @GetMapping(path = "/{second}")
    public Statistics get(@PathVariable("second") Integer second) {
    	return this.statisticsService.generateStatistics(second);
    }

}
