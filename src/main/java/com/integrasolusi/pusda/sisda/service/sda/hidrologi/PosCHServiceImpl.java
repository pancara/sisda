package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.PosCHDao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosCH;
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
 * Date: 8/3/12
 * Time: 8:49 PM
 */
public class PosCHServiceImpl implements PosCHService {
    private PosCHDao posCHDao;
    private BlobRepository blobRepository;

    public void setPosCHDao(PosCHDao posCHDao) {
        this.posCHDao = posCHDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }


    @Override
    public PosCH findById(Long id) {
        return posCHDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_POS_CH, id, os);

    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            posCHDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_POS_CH, id);
        }
    }


    @Override
    public void save(PosCH posCH) {
        posCHDao.save(posCH);
    }

    @Override
    public void save(PosCH posCH, InputStream inputStream) throws IOException {
        posCHDao.save(posCH);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_POS_CH, posCH.getId(), inputStream);
    }

    @Override
    public List<PosCH> findByDas(Das das) {
        return posCHDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "name", OrderDir.ASC);
    }

    @Override
    public List<PosCH> findByWs(WilayahSungai ws) {
        return posCHDao.findByFilter(new ValueFilter("das.wilayahSungai", QueryOperator.EQUALS, ws, "das_ws"), "name", OrderDir.ASC);
    }
}
