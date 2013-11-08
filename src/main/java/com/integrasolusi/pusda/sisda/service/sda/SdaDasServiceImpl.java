package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.SdaDasDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.SdaDas;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/5/12
 * Time: 11:17 AM
 */
public class SdaDasServiceImpl implements SdaDasService {
    private SdaDasDao sdaDasDao;
    private BlobRepository blobRepository;

    public void setSdaDasDao(SdaDasDao sdaDasDao) {
        this.sdaDasDao = sdaDasDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<SdaDas> findByWilayahSungai(WilayahSungai ws) {
        return sdaDasDao.findByFilter(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"), "name", OrderDir.ASC);
    }

    @Override
    public SdaDas findById(Long id) {
        return sdaDasDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_DAS, id, outputStream);
    }
}
