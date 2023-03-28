package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.ProductDTO;
import com.algrince.finaltask.dto.userDTO.UserListDTO;
import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrderProductsService orderProductsService;
    private final OrdersService ordersService;
    private final DTOMapper dtoMapper;


    /**
     *  Calculates revenue between two given dates. Revenue is
     *  defined as a sum of all paid order sums found in DB in the period.
     *
     * @param start     calendar date from which the search of orders begins
     * @param finish    calendar date with which the search of orders ends
     * @return          revenue in the specified period
     */

    public Double getRevenue(Calendar start, Calendar finish) {

        List<Order> orders = ordersService.findAllPaidInPeriod(
                start, finish);

        Double revenue = 0.0;
        for (Order order: orders) {
            revenue += order.getOrderSum();
        }

        return revenue;
    }


    /**
     * Forms a list of lists that represent months. Every nested list
     * contains two elements: start date (1st day of the month) and
     * finish date (last day of the month or the current date). The
     * length of the list (month quantity) is defined by monthCount
     *
     * @param monthCount    quantity of month to define
     * @return              a list of months
     */

    public List<List<Calendar>> getMonths(int monthCount) {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentMonth = Calendar.getInstance();

        // Form the current month (last month to add)

        firstDayCurrentMonth.set(Calendar.DAY_OF_MONTH, 1);
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentMonth);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();


        for (int i = monthCount; i > 1; i--) {

            // Month quantity reduced: one month is already defined

            int month = i - 1;

            // Proceeding to define other months from the first one

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


    /**
     * Forms a list of lists that represent weeks. Every nested list
     * contains two elements: start date (monday) and finish date
     * (sunday or the current day). The length of the list (week quantity)
     * is defined by monthCount.
     *
     * @param weekCount         quantity of week to define
     * @return                  a list of weeks
     */

    public List<List<Calendar>> getWeeks(int weekCount) {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentWeek = Calendar.getInstance();

        // Form the current week (last week to add)

        firstDayCurrentWeek.set(Calendar.DAY_OF_WEEK, 2);
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentWeek);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();


        for (int i = weekCount; i > 1; i--) {

            // Week quantity reduced: one week is already defined

            int week = i - 1;

            // Proceeding to define other weeks from the first one

            Calendar startDay = Calendar.getInstance();
            startDay.set(Calendar.DAY_OF_WEEK, 2);
            startDay.add(Calendar.WEEK_OF_YEAR, -week);

            week--;
            Calendar finishDay = Calendar.getInstance();
            finishDay.set(Calendar.DAY_OF_WEEK, 2);
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

    /**
     * Parses received information about revenue to a format that can be sent
     * through API. For every type of periods (months / weeks) chooses
     * correspondent name, calls method to calculate revenue and packs it into a
     * list of LinkedHashMaps.
     *
     * @param periods           the periods of time for which revenue needs to be calculated
     * @param typeOfRevenue     type of periods (months or weeks)
     * @return                  a full info about revenue divided by passed periods of time
     */

    public List<LinkedHashMap<Object, Object>> getPeriodRevenue(
            List<List<Calendar>> periods, String typeOfRevenue) {
        List<LinkedHashMap<Object, Object>> revenueOfAllPeriods = new ArrayList<>();
        int week = periods.size();

        for (List<Calendar> period : periods) {
            LinkedHashMap<Object, Object> revenueByPeriod = new LinkedHashMap<>();
            Calendar start = period.get(0);
            Calendar finish = period.get(1);

            String periodName = "";
            if (typeOfRevenue.equals("month")) {
                int month = start.get(Calendar.MONTH);
                periodName = new DateFormatSymbols().getMonths()[month];
            } else if (typeOfRevenue.equals("week")) {
                SimpleDateFormat dateFormatForWeek = new SimpleDateFormat("dd.MM");
                String startDate = dateFormatForWeek.format(start.getTime());
                String finishDate = dateFormatForWeek.format(finish.getTime());

                periodName = String.format("Week %s (%s - %s)", week, startDate, finishDate);
                week--;
            }
            Double revenue = getRevenue(start, finish);
            revenueByPeriod.put("period", periodName);
            revenueByPeriod.put("revenue", revenue);
            revenueOfAllPeriods.add(revenueByPeriod);
            }

        return revenueOfAllPeriods;
        }


    /**
     * Generates a list of 10 LinkedHashMaps that contain the most bought
     * products and the times they were bought (occurrence).
     *
      * @return     a list of 10 most bought products with times bought
     */

    public List<LinkedHashMap<Object, Object>> getTop10Products() {
        List<Object[]> top10OrderProducts =
                orderProductsService.findTop10ByProduct();

        List<LinkedHashMap<Object, Object>> topOfProducts = new ArrayList<>();

        for (Object[] productAndOccurrence : top10OrderProducts) {
            LinkedHashMap<Object, Object> topProductInfo = new LinkedHashMap<>();

            Product product = (Product) productAndOccurrence[0];
            Long occurrence = (Long) productAndOccurrence[1];

            topProductInfo.put(
                    "product", dtoMapper.mapClass(product, ProductDTO.class));
            topProductInfo.put("occurrence", occurrence);
            topOfProducts.add(topProductInfo);
        }
        return topOfProducts;
    }


    /**
     * Generates a list of 10 LinkedHashMaps that contain the most active
     * buyers of the store and the times they made an order.
     *
     * @return      a list of the most active buyers
     */

    public List<LinkedHashMap<Object, Object>> getTop10Buyers() {
        List<Object[]> top10OrderBuyers = ordersService.findTop10ByUsers();

        List<LinkedHashMap<Object, Object>> topOfBuyers = new ArrayList<>();

        for (Object[] userAndOccurrence : top10OrderBuyers) {
            LinkedHashMap<Object, Object> topUserInfo = new LinkedHashMap<>();

            User user = (User) userAndOccurrence[0];
            Long occurrence = (Long) userAndOccurrence[1];

            topUserInfo.put(
                    "user", dtoMapper.mapClass(user, UserListDTO.class));
            topUserInfo.put("occurrence", occurrence);
            topOfBuyers.add(topUserInfo);
        }

        return topOfBuyers;
    }
}
