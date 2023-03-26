package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.ProductDTO;
import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.OrderProduct;
import com.algrince.finaltask.models.Product;
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

    public Double getRevenue(Calendar start, Calendar finish) {

        List<Order> orders = ordersService.findAllPaidInPeriod(
                start, finish);

        Double revenue = 0.0;
        for (Order order: orders) {
            revenue += order.getOrderSum();
        }

        return revenue;
    }

    public List<List<Calendar>> getMonths(int monthCount) {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentMonth = Calendar.getInstance();

        firstDayCurrentMonth.set(Calendar.DAY_OF_MONTH, 1);
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentMonth);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();


        for (int i = monthCount; i > 1; i--) {
            int month = i - 1;

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


    public List<List<Calendar>> getWeeks(int weekCount) {
        Calendar today = Calendar.getInstance();
        Calendar firstDayCurrentWeek = Calendar.getInstance();

        firstDayCurrentWeek.set(Calendar.DAY_OF_WEEK, 2);
        List<Calendar> lastPeriod = new ArrayList<>();
        lastPeriod.add(firstDayCurrentWeek);
        lastPeriod.add(today);

        List<List<Calendar>> listOfPeriods = new ArrayList<>();


        for (int i = weekCount; i > 1; i--) {
            int week = i - 1;


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

    public List<LinkedHashMap<Object, Object>> getPeriodRevenue(List<List<Calendar>> periods, String typeOfRevenue) {
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


    public List<LinkedHashMap<Object, Object>> getTop10Products() {
        List<Object[]> top10OrderProducts =
                orderProductsService.findTop10ByProduct();

        List<LinkedHashMap<Object, Object>> topOfProducts = new ArrayList<>();

        for (Object[] productAndOccurrence : top10OrderProducts) {
            LinkedHashMap<Object, Object> topProductInfo = new LinkedHashMap<>();

//            OrderProduct orderProduct = (OrderProduct) productAndOccurrence[0];
//            Product product = orderProduct.getProduct();
            Product product = (Product) productAndOccurrence[0];
            Long occurrence = (Long) productAndOccurrence[1];

            topProductInfo.put(
                    "product", dtoMapper.mapClass(product, ProductDTO.class));
            topProductInfo.put("occurrence", occurrence);
            topOfProducts.add(topProductInfo);
        }
        return topOfProducts;
    }
}
