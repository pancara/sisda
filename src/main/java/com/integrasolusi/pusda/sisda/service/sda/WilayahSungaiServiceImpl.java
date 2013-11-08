package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.WilayahSungaiDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:44 AM
 */
public class WilayahSungaiServiceImpl implements WilayahSungaiService {
    
    private WilayahSungaiDao wilayahSungaiDao;

    public void setWilayahSungaiDao(WilayahSungaiDao wilayahSungaiDao) {
        this.wilayahSungaiDao = wilayahSungaiDao;
    }

    @Override
    public List<WilayahSungai> findAlls() {
        return wilayahSungaiDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public WilayahSungai findById(Long id) {
        return wilayahSungaiDao.findById(id);
    }
}
