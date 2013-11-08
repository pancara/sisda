package com.integrasolusi.pusda.sisda.service.sda.di;

import com.integrasolusi.pusda.sisda.dao.sda.di.DiTypeDao;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiType;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 4:31 PM
 */
public class DiTypeServiceImpl implements DiTypeService {
    private DiTypeDao diTypeDao;
    private BlobRepository blobRepository;

    public void setDiTypeDao(DiTypeDao diTypeDao) {
        this.diTypeDao = diTypeDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<DiType> findAlls() {
        return diTypeDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public DiType findById(Long id) {
        return diTypeDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_DI_TYPE, id, os);
    }

    @Override
    public List<DiType> findAlls(Long start, Long count) {
        return diTypeDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return diTypeDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return diTypeDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword, "name");
    }

    @Override
    public List<DiType> findByKeyword(String keyword, Long start, Long count) {
        return diTypeDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void save(DiType type) {
        diTypeDao.save(type);
    }

    @Override
    public void save(DiType type, InputStream is) throws IOException {
        diTypeDao.save(type);
        blobRepository.store(BlobDataType.SDA_DI_TYPE, type.getId(), is);
    }

    @Override
    public void removeById(Long id) {
        diTypeDao.removeById(id);
        blobRepository.remove(BlobDataType.SDA_DI_TYPE, id);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            diTypeDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_DI_TYPE, id);
        }
    }
}
