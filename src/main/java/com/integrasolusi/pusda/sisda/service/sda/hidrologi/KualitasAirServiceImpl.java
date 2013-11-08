package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.KualitasAirDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.KualitasAir;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 6:45 PM
 */
public class KualitasAirServiceImpl implements KualitasAirService {
    private KualitasAirDao kualitasAirDao;
    private BlobRepository blobRepository;

    public void setKualitasAirDao(KualitasAirDao kualitasAirDao) {
        this.kualitasAirDao = kualitasAirDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public KualitasAir findById(Long id) {
        return kualitasAirDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_KUALITAS_AIR, id, os);

    }

    @Override
    public List<KualitasAir> findByDas(Das das) {
        return kualitasAirDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "year.value", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return kualitasAirDao.countAlls();
    }

    @Override
    public List<KualitasAir> findAlls(Long start, Long count) {
        return kualitasAirDao.findAlls(start, count, "description", OrderDir.ASC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return kualitasAirDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<KualitasAir> findByKeyword(String keyword, Long start, Long count) {
        return kualitasAirDao.findByFilter(createKeywordFilter(keyword), start, count, "description", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            kualitasAirDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_KUALITAS_AIR, id);
        }
    }

    @Override
    public void save(KualitasAir kualitasAir) {
        kualitasAirDao.save(kualitasAir);
    }

    @Override
    public void save(KualitasAir kualitasAir, InputStream inputStream) throws IOException {
        kualitasAirDao.save(kualitasAir);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_KUALITAS_AIR, kualitasAir.getId(), inputStream);
    }
}
