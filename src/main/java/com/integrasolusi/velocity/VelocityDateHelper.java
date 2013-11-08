package com.integrasolusi.velocity;

import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.tools.generic.DateTool;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer   : pancara
 * Date         : 7/5/11
 * Time         : 8:43 AM
 */
public class VelocityDateHelper extends DateTool {


    /**
     * @param month month index, starting by 1
     * @return month name as string
     */
    public String getMonthString(int month) {
        return new DateFormatSymbols(getLocale()).getMonths()[month];
    }

    public String getShortMonthString(int month) {
        return new DateFormatSymbols(getLocale()).getShortMonths()[month];
    }

    public int getDayOfWeek(java.util.Date date) {
        Calendar cal = new GregorianCalendar(getLocale());
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public boolean isSameDay(java.util.Date d1, java.util.Date d2) {
        return DateUtils.isSameDay(d1, d2);
    }

    public boolean isSameMonthYear(java.util.Date d1, java.util.Date d2) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(d1);

        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(d2);
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}
