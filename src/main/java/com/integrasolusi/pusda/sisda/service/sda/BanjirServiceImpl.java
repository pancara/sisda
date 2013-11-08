package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.BanjirDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.Banjir;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 10:52 PM
 */
public class BanjirServiceImpl implements BanjirService {
    private BanjirDao banjirDao;
    private BlobRepository blobRepository;

    public void setBanjirDao(BanjirDao banjirDao) {
        this.banjirDao = banjirDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<Banjir> findByDas(Das das) {
        return banjirDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "year.value", OrderDir.ASC);
    }

    @Override
    public Banjir findById(Long id) {
        return banjirDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_BANJIR, id, os);
    }

    @Override
    public Long countAlls() {
        return banjirDao.countAlls();
    }

    @Override
    public List<Banjir> findAlls(Long start, Long count) {
        return banjirDao.findAlls(start, count, "year", OrderDir.ASC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return banjirDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<Banjir> findByKeyword(String keyword, Long start, Long count) {
        return banjirDao.findByFilter(createKeywordFilter(keyword), start, count, "year", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            banjirDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_BANJIR, id);
        }
    }

    @Override
    public void save(Banjir banjir) {
        banjirDao.save(banjir);
    }

    @Override
    public void save(Banjir banjir, InputStream inputStream) throws IOException {
        banjirDao.save(banjir);
        blobRepository.store(BlobDataType.SDA_BANJIR, banjir.getId(), inputStream);
    }
}
