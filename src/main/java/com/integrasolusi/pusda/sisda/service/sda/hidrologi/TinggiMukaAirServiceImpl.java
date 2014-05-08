package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.TinggiMukaAirDao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.TinggiMukaAir;
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
 * Date: 8/4/12
 * Time: 11:02 AM
 */
public class TinggiMukaAirServiceImpl implements TinggiMukaAirService {

    private TinggiMukaAirDao tinggiMukaAirDao;
    private BlobRepository blobRepository;


    public void setTinggiMukaAirDao(TinggiMukaAirDao tinggiMukaAirDao) {
        this.tinggiMukaAirDao = tinggiMukaAirDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public TinggiMukaAir findById(Long id) {
        return tinggiMukaAirDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_TMA, id, os);
    }

    @Override
    public Long countAlls() {
        return tinggiMukaAirDao.countAlls();
    }

    @Override
    public List<TinggiMukaAir> findAlls(Long start, Long count) {
        return tinggiMukaAirDao.findAlls(start, count, "description", OrderDir.ASC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return tinggiMukaAirDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<TinggiMukaAir> findByKeyword(String keyword, Long start, Long count) {
        return tinggiMukaAirDao.findByFilter(createKeywordFilter(keyword), start, count, "description", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            tinggiMukaAirDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_TMA, id);
        }
    }

    @Override
    public void save(TinggiMukaAir tinggiMukaAir) {
        tinggiMukaAirDao.save(tinggiMukaAir);
    }

    @Override
    public void save(TinggiMukaAir tinggiMukaAir, InputStream inputStream) throws IOException {
        tinggiMukaAirDao.save(tinggiMukaAir);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_TMA, tinggiMukaAir.getId(), inputStream);
    }

    @Override
    public List<TinggiMukaAir> findByDas(Das das) {
        return tinggiMukaAirDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "description", OrderDir.ASC);
    }

}
