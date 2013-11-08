package com.integrasolusi.pusda.sisda.web.form.component;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer   : pancara
 * Date         : 7/5/11
 * Time         : 8:08 AM
 */
public class DateTimeComponent {
    private Integer date;
    private Integer month;
    private Integer year;

    private Integer hour;
    private Integer minute;

    public DateTimeComponent() {
        Calendar cal = GregorianCalendar.getInstance();
        setCalendar(cal);
    }

    public DateTimeComponent(Integer date, Integer month, Integer year, Integer hour, Integer minute) {
        this.setDate(date);
        this.setMonth(month);
        this.setYear(year);

        this.setHour(hour);
        this.setMinute(minute);
    }

    public void setCalendar(Calendar cal) {
        synchronized (this) {
            date = cal.get(Calendar.DATE);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);

            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
        }
    }

    public synchronized Calendar getCalendar() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, date, hour, minute);
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

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }
}
