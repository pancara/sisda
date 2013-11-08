package com.integrasolusi.pusda.sisda.service.sda.bendung;

import com.integrasolusi.pusda.sisda.dao.sda.bendung.BendungSummaryDao;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.BendungSummary;
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
public class BendungSummaryServiceImpl implements BendungSummaryService {

    private BendungSummaryDao bendungSummaryDao;

    private BlobRepository blobRepository;

    public void setBendungSummaryDao(BendungSummaryDao bendungSummaryDao) {
        this.bendungSummaryDao = bendungSummaryDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public BendungSummary findById(Long id) {
        return bendungSummaryDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_BENDUNG_SUMMARY, id, os);
    }

    @Override
    public void save(BendungSummary sungaiSummary) {
        bendungSummaryDao.save(sungaiSummary);
    }

    @Override
    public void save(BendungSummary sungaiSummary, InputStream inputStream) throws IOException {
        bendungSummaryDao.save(sungaiSummary);
        blobRepository.store(BlobDataType.SDA_BENDUNG_SUMMARY, sungaiSummary.getId(), inputStream);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            bendungSummaryDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_BENDUNG_SUMMARY, id);
        }
    }

    @Override
    public BendungSummary findByWilayahSungai(WilayahSungai ws) {
        return bendungSummaryDao.findUniqueByFilter(new ValueFilter("ws", QueryOperator.EQUALS, ws, "ws"));
    }
}
