package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.KabupatenDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Kabupaten;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;

import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:35 AM
 */
public class KabupatenServiceImpl implements KabupatenService {
    private KabupatenDao kabupatenDao;

    public void setKabupatenDao(KabupatenDao kabupatenDao) {
        this.kabupatenDao = kabupatenDao;
    }

    @Override
    public List<Kabupaten> findByPropinsi(Propinsi propinsi) {
        return kabupatenDao.findByFilter(new ValueFilter("propinsi", QueryOperator.EQUALS, propinsi, "propinsi"), "name", OrderDir.ASC);
    }

    @Override
    public List<Kabupaten> findAlls() {
        return kabupatenDao.findAlls("name", OrderDir.ASC);
    }

    @Override
    public Kabupaten findById(Long id) {
        return kabupatenDao.findById(id);
    }
}
