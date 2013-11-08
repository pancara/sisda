package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.TelagaDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.Telaga;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 2:54 PM
 */
public class TelagaServiceImpl implements TelagaService {
    private TelagaDao telagaDao;

    public void setTelagaDao(TelagaDao telagaDao) {
        this.telagaDao = telagaDao;
    }

    @Override
    public Telaga findById(Long id) {
        return telagaDao.findById(id);
    }

    @Override
    public List<Telaga> findByDas(Das das) {
        return telagaDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "id", OrderDir.ASC);
    }

    @Override
    public void save(Telaga telaga) {
        telagaDao.save(telaga);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            telagaDao.removeById(id);
        }
    }

    @Override
    public Long countAlls() {
        return telagaDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return telagaDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public Long countByDas(Long das) {
        return telagaDao.countByFilter(createDasFilter(das));
    }

    @Override
    public Long countByDasAndKeyword(Long das, String keyword) {
        return telagaDao.countByFilter(createDasAndKeywordFilter(das, keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword, "name");
    }

    private Filter createDasFilter(Long das) {
        return new ValueFilter("das.id", QueryOperator.EQUALS, das, "das_id");
    }

    private CompositeFilter createDasAndKeywordFilter(Long das, String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createKeywordFilter(keyword));
        filter.add(createDasFilter(das));
        return filter;
    }

    @Override
    public List<Telaga> findAlls(Long start, Long count) {
        return telagaDao.findAlls(start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Telaga> findByKeyword(String keyword, Long start, Long count) {
        return telagaDao.findByFilter(createKeywordFilter(keyword), start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Telaga> findByDas(Long das, Long start, Long count) {
        return telagaDao.findByFilter(createDasFilter(das), start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Telaga> findByDasAndKeyword(Long das, String keyword, Long start, Long count) {
        return telagaDao.findByFilter(createDasAndKeywordFilter(das, keyword), start, count, "name", OrderDir.ASC);
    }

}
