package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.EmbungPotensiDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;
import com.integrasolusi.pusda.sisda.persistence.sda.EmbungPotensi;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 3:09 PM
 */
public class EmbungPotensiServiceImpl implements EmbungPotensiService {
    private EmbungPotensiDao embungPotensiDao;

    public void setEmbungPotensiDao(EmbungPotensiDao embungPotensiDao) {
        this.embungPotensiDao = embungPotensiDao;
    }

    @Override
    public List<EmbungPotensi> findByPropinsi(Propinsi propinsi) {
        return embungPotensiDao.findByFilter(new ValueFilter("propinsi", QueryOperator.EQUALS, propinsi, "propinsi"));
    }

    @Override
    public EmbungPotensi findById(Long id) {
        return embungPotensiDao.findById(id);
    }

    @Override
    public List<EmbungPotensi> findByDas(Das das) {
        return embungPotensiDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "id", OrderDir.ASC);
    }
}
