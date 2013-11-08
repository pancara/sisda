package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.PosTMADao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosTMA;
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
public class PosTMAServiceImpl implements PosTMAService {
    private PosTMADao posTMADao;
    private BlobRepository blobRepository;

    public void setPosTMADao(PosTMADao posTMADao) {
        this.posTMADao = posTMADao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }


    @Override
    public PosTMA findById(Long id) {
        return posTMADao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_POS_TMA, id, os);

    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            posTMADao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_POS_TMA, id);
        }
    }


    @Override
    public void save(PosTMA posTMA) {
        posTMADao.save(posTMA);
    }

    @Override
    public void save(PosTMA posTMA, InputStream inputStream) throws IOException {
        posTMADao.save(posTMA);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_POS_TMA, posTMA.getId(), inputStream);
    }

    @Override
    public List<PosTMA> findByDas(Das das) {
        return posTMADao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "name", OrderDir.ASC);
    }
}
