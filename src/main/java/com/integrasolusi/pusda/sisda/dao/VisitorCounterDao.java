package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.persistence.VisitorCounter;
import com.integrasolusi.query.generic.GenericDao;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 1:16 PM
 */
public interface VisitorCounterDao extends GenericDao<VisitorCounter, Long> {
    Long getTotal();

    Long getTotal(Date start, Date end);

    Long getTotal(Integer month, Integer year);
}
