package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.EmbungDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;
import com.integrasolusi.pusda.sisda.persistence.sda.Embung;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 2:54 PM
 */
public class EmbungServiceImpl implements EmbungService {
    private EmbungDao embungDao;

    public void setEmbungDao(EmbungDao embungDao) {
        this.embungDao = embungDao;
    }

    @Override
    public List<Embung> findByPropinsi(Propinsi propinsi) {
        return embungDao.findByFilter(new ValueFilter("propinsi", QueryOperator.EQUALS, propinsi, "propinsi"), "id", OrderDir.ASC);
    }

    @Override
    public Embung findById(Long id) {
        return embungDao.findById(id);
    }

    @Override
    public List<Embung> findByDas(Das das) {
        return embungDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "id", OrderDir.ASC);
    }

    @Override
    public void save(Embung embung) {
        embungDao.save(embung);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            embungDao.removeById(id);
        }
    }

    @Override
    public Long countAlls() {
        return embungDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return embungDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public Long countByDas(Long das) {
        return embungDao.countByFilter(createDasFilter(das));
    }

    @Override
    public Long countByDasAndKeyword(Long das, String keyword) {
        return embungDao.countByFilter(createDasAndKeywordFilter(das, keyword));
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
    public List<Embung> findAlls(Long start, Long count) {
        return embungDao.findAlls(start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Embung> findByKeyword(String keyword, Long start, Long count) {
        return embungDao.findByFilter(createKeywordFilter(keyword), start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Embung> findByDas(Long das, Long start, Long count) {
        return embungDao.findByFilter(createDasFilter(das), start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Embung> findByDasAndKeyword(Long das, String keyword, Long start, Long count) {
        return embungDao.findByFilter(createDasAndKeywordFilter(das, keyword), start, count, "name", OrderDir.ASC);
    }
}
