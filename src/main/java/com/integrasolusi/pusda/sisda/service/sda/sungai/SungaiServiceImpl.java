package com.integrasolusi.pusda.sisda.service.sda.sungai;

import com.integrasolusi.pusda.sisda.dao.sda.sungai.SungaiDao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.Sungai;
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
 * Time: 12:37 PM
 */
public class SungaiServiceImpl implements SungaiService {
    private SungaiDao sungaiDao;
    private BlobRepository blobRepository;

    public void setSungaiDao(SungaiDao sungaiDao) {
        this.sungaiDao = sungaiDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<Sungai> findAlls() {
        return sungaiDao.findAlls();
    }

    @Override
    public Sungai findById(Long id) {
        return sungaiDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_SUNGAI, id, os);
    }


    @Override
    public Long countAlls() {
        return sungaiDao.countAlls();
    }


    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword, "name");
    }


    private Filter createWilayahSungaiFilter(Long wilayahSungai) {
        return new ValueFilter("wilayahSungai.id", QueryOperator.EQUALS, wilayahSungai, "wilayahSungai_id");
    }


    @Override
    public List<Sungai> findAlls(Long start, Long count) {
        return sungaiDao.findAlls(start, count);
    }

    @Override
    public void save(Sungai sungai) {
        sungaiDao.save(sungai);
    }

    @Override
    public void save(Sungai sungai, InputStream inputStream) throws IOException {
        sungaiDao.save(sungai);
        blobRepository.store(BlobDataType.SDA_SUNGAI, sungai.getId(), inputStream);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            sungaiDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_SUNGAI, id);
        }
    }

    @Override
    public List<Sungai> findByDas(Das das) {
        return sungaiDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "name", OrderDir.ASC);
    }
}
