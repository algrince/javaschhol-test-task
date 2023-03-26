package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrdersService ordersService;

    public Double getRevenue(Calendar start, Calendar finish) {

        List<Order> orders = ordersService.findAllPaidInPeriod(
                start, finish);

        Double revenue = 0.0;
        for (Order order: orders) {
            revenue += order.getOrderSum();
        }

        return revenue;
    }

    public List<List<Calendar>> getMonths() {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentMonth = Calendar.getInstance();

        log.info("Before: " + firstDayCurrentMonth.getTime());
        firstDayCurrentMonth.set(Calendar.DAY_OF_MONTH, 1);
        log.info("After: " + firstDayCurrentMonth.getTime());
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentMonth);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();

        int monthCount = 6;

        for (int i = monthCount; i >= 1; i--) {
            int month = i;

            Calendar startDay = Calendar.getInstance();
            startDay.set(Calendar.DAY_OF_MONTH, 1);
            startDay.add(Calendar.MONTH, -month);

            month--;
            Calendar finishDay = Calendar.getInstance();
            finishDay.set(Calendar.DAY_OF_MONTH, 1);
            finishDay.add(Calendar.MONTH, -month);
            finishDay.add(Calendar.DAY_OF_MONTH, -1);

            List<Calendar> period = new ArrayList<>();
            period.add(startDay);
            period.add(finishDay);

            listOfPeriods.add(period);
        }

        listOfPeriods.add(lastPeriod);

        return listOfPeriods;
    }


    public List<List<Calendar>> getWeeks() {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentWeek = Calendar.getInstance();
        firstDayCurrentWeek.set(Calendar.DAY_OF_WEEK, 1);
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentWeek);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();

        int weekCount = 4;

        for (int i = weekCount; i >= 1; i--) {
            int week = i;

            Calendar startDay = Calendar.getInstance();
            startDay.set(Calendar.DAY_OF_WEEK, 1);
            startDay.add(Calendar.WEEK_OF_YEAR, -week);

            week--;
            Calendar finishDay = Calendar.getInstance();
            finishDay.set(Calendar.DAY_OF_WEEK, 1);
            finishDay.add(Calendar.WEEK_OF_YEAR, -week);
            finishDay.add(Calendar.DAY_OF_WEEK, -1);

            List<Calendar> period = new ArrayList<>();
            period.add(startDay);
            period.add(finishDay);

            listOfPeriods.add(period);
        }

        listOfPeriods.add(lastPeriod);

        return listOfPeriods;
    }

    public Map<String, Double> getPeriodRevenue(List<List<Calendar>> periods, String typeOfRevenue) {
        Map<String, Double> revenueByPeriod = new HashMap<>();
        for (List<Calendar> period : periods) {
            Calendar start = period.get(0);
            Calendar finish = period.get(1);

            String periodName = "";
            if (typeOfRevenue.equals("month")) {
                int month = start.get(Calendar.MONTH);
                periodName = new DateFormatSymbols().getMonths()[month];
            } else if (typeOfRevenue.equals("week")) {
                int week = 1;
                periodName = "Week " + week;
            }
            Double revenue = getRevenue(start, finish);
            revenueByPeriod.put(periodName, revenue);
            }

        return revenueByPeriod;
        }
}
