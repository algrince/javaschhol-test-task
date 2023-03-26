package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrdersService ordersService;

    public Double getRevenue(Calendar start, Calendar finish) {

        List<Order> orders = ordersService.findAllPaidInPeriod(
                start, finish);

        Double revenue = 0.0;
        for (Order order: orders) {
            revenue =+ order.getOrderSum();
        }

        return revenue;
    }

    public List<List<Calendar>> getMonths() {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentMonth = Calendar.getInstance();
        firstDayCurrentMonth.set(Calendar.DAY_OF_MONTH, 1);
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentMonth);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();

        int monthCount = 6;

        for (int i = monthCount; i <= 1; i--) {
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

    public Map<String, Double> getPeriodRevenue(List<List<Calendar>> periods) {
        Map<String, Double> revenueByPeriod = new HashMap<>();
        for (List<Calendar> period : periods) {
            Calendar start = period.get(0);
            Calendar finish = period.get(1);

            int month = start.get(Calendar.MONTH);
            String monthString = new DateFormatSymbols().getMonths()[month];

            Double revenue = getRevenue(start, finish);
            revenueByPeriod.put(monthString, revenue);
            }

        return revenueByPeriod;
        }
}
