package com.integrasolusi.pusda.sisda.web.form.component;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer   : pancara
 * Date         : 7/5/11
 * Time         : 8:08 AM
 */
public class DateComponent {
    private Integer date;
    private Integer month;
    private Integer year;

    public DateComponent() {
        Calendar cal = GregorianCalendar.getInstance();
        setCalendar(cal);
    }

    public DateComponent(Integer date, Integer month, Integer year) {
        this.setDate(date);
        this.setMonth(month);
        this.setYear(year);
    }

    public synchronized void setCalendar(Calendar cal) {
        this.date = cal.get(Calendar.DATE);
        this.month = cal.get(Calendar.MONTH);
        this.year = cal.get(Calendar.YEAR);
    }

    public synchronized java.util.Calendar getCalendar() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, date, 0, 0);
        return cal;
    }


    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
