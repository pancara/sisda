package com.integrasolusi.pusda.sisda.web.controller;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Programmer   : pancara
 * Date         : 7/8/11
 * Time         : 12:32 PM
 */
public class CalendarControllerTest {

    @Test
    public void createCalendar() throws Exception {
        Calendar today = new GregorianCalendar(2011, 03, 01);

        Calendar startDate = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("startDate = " + startDate.getTime());

        Calendar endDate = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("endDate = " + endDate.getTime());

        startDate.set(Calendar.DAY_OF_MONTH, 2 - startDate.get(Calendar.DAY_OF_WEEK));
        int day_of_month = endDate.getMaximum(Calendar.DAY_OF_MONTH) + 6 - endDate.get(Calendar.DAY_OF_WEEK);
        System.out.println("day_of_month = " + day_of_month);
        endDate.set(Calendar.DAY_OF_MONTH, day_of_month);

        System.out.println("startDate = " + startDate.getTime());
        System.out.println("endDate = " + endDate.getTime());

        List<Map<Date, Integer>> calendar = new LinkedList<Map<Date, Integer>>();
        Map<Date, Integer> week = null;
        Calendar day = startDate;
        Integer number = 1;
        while (true) {
            if (1 == day.get(Calendar.DAY_OF_WEEK)) {
                week = new LinkedHashMap<Date, Integer>();
                calendar.add(week);
            }
            week.put(day.getTime(), number);

            number++;
            day.set(Calendar.DAY_OF_MONTH, day.get(Calendar.DAY_OF_MONTH) + 1);

            if (day.after(endDate)) {
                break;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        for (Map<Date, Integer> w : calendar) {
            for (Date d : w.keySet()) {
                System.out.print(formatter.format(d));
                System.out.print(" | ");
            }
            System.out.println();

        }
    }
}
