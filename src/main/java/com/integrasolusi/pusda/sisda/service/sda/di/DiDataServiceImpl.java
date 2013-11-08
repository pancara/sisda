package com.integrasolusi.pusda.sisda.service.sda.di;

import com.integrasolusi.pusda.sisda.dao.sda.di.DiDataDao;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiData;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiDi;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 11:20 AM
 */
public class DiDataServiceImpl implements DiDataService {
    private DiDataDao dataDao;
    private BlobRepository blobRepository;

    public void setDiDataDao(DiDataDao dataDao) {
        this.dataDao = dataDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public DiData findById(Long id) {
        return dataDao.findById(id);
    }

    @Override
    public Long countAlls() {
        return dataDao.countAlls();
    }

    @Override
    public List<DiData> findAlls() {
        return dataDao.findAlls("name", OrderDir.ASC);
    }

    @Override
    public List<DiData> findAlls(Long start, Long count) {
        return dataDao.findAlls(start, count, "name", OrderDir.DESC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        Filter filter = createKeywordFilter(keyword);
        return dataDao.countByFilter(filter);
    }

    @Override
    public List<DiData> findByKeyword(String keyword, Long start, Long count) {
        Filter filter = createKeywordFilter(keyword);
        return dataDao.findByFilter(filter, start, count, "name", OrderDir.ASC);
    }

    @Override
    public void save(DiData data) {
        dataDao.save(data);
    }

    @Override
    public void save(DiData data, InputStream inputStream) throws IOException {
        dataDao.save(data);
        blobRepository.store(BlobDataType.SDA_DI_DATA, data.getId(), inputStream);
    }

    @Override
    public void getBlob(Long typeId, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_DI_DATA, typeId, os);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            dataDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_DI_DATA, id);
        }
    }


    @Override
    public List<DiData> findByDi(DiDi di) {
        return dataDao.findByFilter(new ValueFilter("di", QueryOperator.EQUALS, di, "di"), "name", OrderDir.ASC);
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword);
    }

    @Override
    public List<DiData> findAlls(String orderProperty, OrderDir orderDir) {
        return dataDao.findAlls("name", OrderDir.ASC);
    }
}
