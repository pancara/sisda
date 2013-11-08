package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.YearDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Year;

import java.util.List;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:50 PM
 */
public class YearServiceImpl implements YearService {
    private YearDao yearDao;

    public void setYearDao(YearDao yearDao) {
        this.yearDao = yearDao;
    }

    @Override
    public Year findByValue(Integer yearValue) {
        return yearDao.findUniqueByFilter(new ValueFilter("value", QueryOperator.EQUALS, yearValue, "value"));
    }

    @Override
    public List<Year> findAlls() {
        return yearDao.findAlls("value", OrderDir.ASC);
    }

    @Override
    public Year findById(Long id) {
        return yearDao.findById(id);
    }
}
