package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.KekeringanDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 9/5/12
 * Time: 11:59 AM
 */
public class KekeringanServiceImpl implements KekeringanService {
    private BlobRepository blobRepository;
    private KekeringanDao kekeringanDao;

    public void setKekeringanDao(KekeringanDao kekeringanDao) {
        this.kekeringanDao = kekeringanDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<Year> findYearByWilayahSungai(WilayahSungai ws) {
        return kekeringanDao.getYearByWilayahSungai(ws);
    }

    @Override
    public List<Kekeringan> findByWilayahSungaiAndYear(WilayahSungai ws, Year year) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"));
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "year"));
        return kekeringanDao.findByFilter(filter, "id", OrderDir.ASC);
    }

    @Override
    public Kekeringan findById(Long id) {
        return kekeringanDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_KEKERINGAN, id, outputStream);
    }

    @Override
    public void save(Kekeringan kekeringan, InputStream is) throws IOException {
        kekeringanDao.save(kekeringan);
        blobRepository.store(BlobDataType.SDA_KEKERINGAN, kekeringan.getId(), is);
    }

    @Override
    public void save(Kekeringan kekeringan) {
        kekeringanDao.save(kekeringan);
    }

    @Override
    public List<Kekeringan> findAlls(Long start, Long count) {
        return kekeringanDao.findAlls(start, count);
    }

    @Override
    public List<Kekeringan> findByKeyword(String keyword, Long start, Long count) {
        return kekeringanDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<Kekeringan> findByYear(Long year, Long start, Long count) {
        return kekeringanDao.findByFilter(createYearFilter(year), start, count, "id", OrderDir.ASC);
    }

    @Override
    public List<Kekeringan> findByYearAndKeyword(Long year, String keyword, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createYearFilter(year));
        filter.add(createKeywordFilter(keyword));
        return kekeringanDao.findByFilter(filter, start, count, "id", OrderDir.ASC);
    }

    private List<Kekeringan> getByFilter(Long start, Long count, CompositeFilter filter) {
        return kekeringanDao.findByFilter(filter, start, count, "id", OrderDir.ASC);
    }

    private Filter createYearFilter(Long year) {
        return new ValueFilter("year.id", QueryOperator.EQUALS, year, "year_id");
    }

    @Override
    public Long countAlls() {
        return kekeringanDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return kekeringanDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public Long countByYear(Long year) {
        return kekeringanDao.countByFilter(createYearFilter(year));
    }

    @Override
    public Long countByYearAndKeyword(Long year, String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createYearFilter(year));
        filter.add(createKeywordFilter(keyword));
        return kekeringanDao.countByFilter(filter);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            kekeringanDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_KEKERINGAN, id);
        }
    }
}
