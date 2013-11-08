package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.PosKlimatologiDao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosKlimatologi;
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
public class PosKlimatologiServiceImpl implements PosKlimatologiService {
    private PosKlimatologiDao posKlimatologiDao;
    private BlobRepository blobRepository;

    public void setPosKlimatologiDao(PosKlimatologiDao posKlimatologiDao) {
        this.posKlimatologiDao = posKlimatologiDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }


    @Override
    public PosKlimatologi findById(Long id) {
        return posKlimatologiDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_POS_KLIMATOLOGI, id, os);

    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            posKlimatologiDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_POS_KLIMATOLOGI, id);
        }
    }


    @Override
    public void save(PosKlimatologi posCH) {
        posKlimatologiDao.save(posCH);
    }

    @Override
    public void save(PosKlimatologi posCH, InputStream inputStream) throws IOException {
        posKlimatologiDao.save(posCH);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_POS_KLIMATOLOGI, posCH.getId(), inputStream);
    }

    @Override
    public List<PosKlimatologi> findByDas(Das das) {
        return posKlimatologiDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "name", OrderDir.ASC);
    }

    @Override
    public List<PosKlimatologi> findByWs(WilayahSungai ws) {
        return posKlimatologiDao.findByFilter(new ValueFilter("das.wilayahSungai", QueryOperator.EQUALS, ws, "ws"), "name", OrderDir.ASC);
    }
}
