package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.DasDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 8:48 AM
 */
public class DasServiceImpl implements DasService {
    private DasDao dasDao;

    public void setDasDao(DasDao dasDao) {
        this.dasDao = dasDao;
    }

    @Override
    public List<Das> findAlls() {
        return dasDao.findAlls("name", OrderDir.ASC);
    }

    @Override
    public List<Das> findByWilayahSungai(WilayahSungai ws) {
        return dasDao.findByFilter(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"), "name", OrderDir.ASC);
    }

    @Override
    public Das findById(Long id) {
        return dasDao.findById(id);
    }


}
