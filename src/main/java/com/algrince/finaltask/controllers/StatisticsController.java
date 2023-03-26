package com.algrince.finaltask.controllers;

import com.algrince.finaltask.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("monthly_revenue")
    public Map<String, Double> getMonthlyRevenue() {
        List<List<Calendar>> months = statisticsService.getMonths();
        Map<String, Double> revenue = statisticsService.getPeriodRevenue(months);
        return revenue;
    }
}
