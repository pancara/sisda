package com.integrasolusi.pusda.sisda.service.sda.sungai;

import com.integrasolusi.pusda.sisda.dao.sda.sungai.SungaiSummaryDao;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.SungaiSummary;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 12:37 PM
 */
public class SungaiSummaryServiceImpl implements SungaiSummaryService {

    private SungaiSummaryDao sungaiSummaryDao;

    private BlobRepository blobRepository;

    public void setSungaiSummaryDao(SungaiSummaryDao sungaiSummaryDao) {
        this.sungaiSummaryDao = sungaiSummaryDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public SungaiSummary findById(Long id) {
        return sungaiSummaryDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_SUNGAI_SUMMARY, id, os);
    }

    @Override
    public void save(SungaiSummary sungaiSummary) {
        sungaiSummaryDao.save(sungaiSummary);
    }

    @Override
    public void save(SungaiSummary sungaiSummary, InputStream inputStream) throws IOException {
        sungaiSummaryDao.save(sungaiSummary);
        blobRepository.store(BlobDataType.SDA_SUNGAI_SUMMARY, sungaiSummary.getId(), inputStream);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            sungaiSummaryDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_SUNGAI_SUMMARY, id);
        }
    }

    @Override
    public SungaiSummary findByWilayahSungai(WilayahSungai ws) {
        return sungaiSummaryDao.findUniqueByFilter(new ValueFilter("ws", QueryOperator.EQUALS, ws, "ws"));
    }
}
