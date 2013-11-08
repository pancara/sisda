package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.SdaDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Sda;

import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 1:56 PM
 */
public class SdaServiceImpl implements SdaService {
    private SdaDao sdaDao;

    public void setSdaDao(SdaDao sdaDao) {
        this.sdaDao = sdaDao;
    }

    @Override
    public List<Sda> findActive() {
        return sdaDao.findByFilter(new ValueFilter("published", QueryOperator.EQUALS, true, "published"), "index", OrderDir.ASC);
    }

    @Override
    public Sda findById(Long id) {
        return sdaDao.findById(id);
    }
}
