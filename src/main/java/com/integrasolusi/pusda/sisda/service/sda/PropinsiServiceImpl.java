package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.PropinsiDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;

import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:30 AM
 */
public class PropinsiServiceImpl implements PropinsiService {
    private PropinsiDao propinsiDao;

    public void setPropinsiDao(PropinsiDao propinsiDao) {
        this.propinsiDao = propinsiDao;
    }

    @Override
    public List<Propinsi> findAlls() {
        return propinsiDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public Propinsi findById(Long id) {
        return propinsiDao.findById(id);
    }
}
