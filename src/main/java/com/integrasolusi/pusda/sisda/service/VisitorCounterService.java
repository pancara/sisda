package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.VisitorCounter;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 1:18 PM
 */
public interface VisitorCounterService {
    void incrementCount(Date date);

    VisitorCounter findByDate(Date date);

    Long getTotal();

    Long findInDate(Date start, Date end);

    Long findInMonth(Integer month, Integer year);
}
