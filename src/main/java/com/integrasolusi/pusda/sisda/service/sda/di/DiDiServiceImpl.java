package com.integrasolusi.pusda.sisda.service.sda.di;

import com.integrasolusi.pusda.sisda.dao.sda.di.DiDiDao;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiDi;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiType;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 4:31 PM
 */
public class DiDiServiceImpl implements DiDiService {
    private DiDiDao diDiDao;
    private BlobRepository blobRepository;


    public void setDiDiDao(DiDiDao diDiDao) {
        this.diDiDao = diDiDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<DiDi> findAlls() {
        return diDiDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public DiDi findById(Long id) {
        return diDiDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_DI_DI, id, os);
    }

    @Override
    public List<DiDi> findAlls(Long start, Long count) {
        return diDiDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return diDiDao.countAlls();
    }

    @Override
    public void save(DiDi di) {
        diDiDao.save(di);
    }

    @Override
    public void save(DiDi di, InputStream is) throws IOException {
        diDiDao.save(di);
        blobRepository.store(BlobDataType.SDA_DI_DI, di.getId(), is);
    }

    @Override
    public void removeById(Long id) {
        diDiDao.removeById(id);
        blobRepository.remove(BlobDataType.SDA_DI_DI, id);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            diDiDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_DI_DI, id);
        }
    }

    @Override
    public List<DiDi> findByDiType(DiType type) {
        return diDiDao.findByFilter(new ValueFilter("type", QueryOperator.EQUALS, type, "type"), "name", OrderDir.ASC);
    }
}
