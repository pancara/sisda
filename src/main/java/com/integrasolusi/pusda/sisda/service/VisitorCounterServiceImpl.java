package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.VisitorCounterDao;
import com.integrasolusi.pusda.sisda.persistence.VisitorCounter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 1:18 PM
 */
public class VisitorCounterServiceImpl implements VisitorCounterService {
    private VisitorCounterDao visitorCounterDao;

    public void setVisitorCounterDao(VisitorCounterDao visitorCounterDao) {
        this.visitorCounterDao = visitorCounterDao;
    }

    @Override
    public synchronized void incrementCount(Date date) {
        VisitorCounter counter = visitorCounterDao.findUniqueByFilter(new ValueFilter("date", QueryOperator.EQUALS, date, "date"));
        if (counter == null) {
            counter = new VisitorCounter();
            counter.setDate(date);
            counter.setCount(1L);
            visitorCounterDao.save(counter);
        } else {
            counter.setCount(counter.getCount() + 1);
            visitorCounterDao.save(counter);
        }
    }

    @Override
    public VisitorCounter findByDate(Date date) {
        return visitorCounterDao.findUniqueByFilter(new ValueFilter("date", QueryOperator.EQUALS, date, "date"));
    }

    @Override
    public Long getTotal() {
        return visitorCounterDao.getTotal();
    }

    @Override
    public Long findInDate(Date start, Date end) {
        return visitorCounterDao.getTotal(start, end);
    }

    @Override
    public Long findInMonth(Integer month, Integer year) {
        return visitorCounterDao.getTotal(month, year);
    }
}
