package com.integrasolusi.pusda.sisda.web.form.component;

import org.junit.Test;

import java.util.Calendar;

/**
 * Programmer   : pancara
 * Date         : 7/5/11
 * Time         : 8:13 AM
 */
public class DateComponentTest {
    @Test
    public void testSetCalendar() throws Exception {
        DateComponent date = new DateComponent();

        date.setDate(10);
        date.setMonth(0);
        date.setYear(2011);

        Calendar cal = date.getCalendar();

        System.out.println("cal = " + cal.getTime());

    }

    @Test
    public void testGetCalendar() throws Exception {

    }
}
