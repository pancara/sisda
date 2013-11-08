package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.AirTanahDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.AirTanah;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 3:18 PM
 */
public class AirTanahServiceImpl implements AirTanahService {
    private AirTanahDao airTanahDao;
    private BlobRepository blobRepository;

    public void setAirTanahDao(AirTanahDao airTanahDao) {
        this.airTanahDao = airTanahDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<AirTanah> findByWilayahSungai(WilayahSungai ws) {
        return airTanahDao.findByFilter(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"), "id", OrderDir.ASC);
    }

    @Override
    public AirTanah findById(Long id) {
        return airTanahDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_AIR_TANAH, id, outputStream);
    }

    @Override
    public Long countAlls() {
        return airTanahDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return airTanahDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<AirTanah> findAlls(Long start, Long count) {
        return airTanahDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public List<AirTanah> findByKeyword(String keyword, Long start, Long count) {
        return airTanahDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void save(AirTanah airTanah, InputStream is) throws IOException {
        airTanahDao.save(airTanah);
        blobRepository.store(BlobDataType.SDA_AIR_TANAH, airTanah.getId(), is);
    }


    @Override
    public void save(AirTanah airTanah) {
        airTanahDao.save(airTanah);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            airTanahDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_AIR_TANAH, id);
        }
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }
}
