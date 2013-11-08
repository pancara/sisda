package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.DaerahRawanBanjirDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.DaerahRawanBanjir;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 3:25 PM
 */
public class DaerahRawanBanjirServiceImpl implements DaerahRawanBanjirService {
    private DaerahRawanBanjirDao daerahRawanBanjirDao;
    private BlobRepository blobRepository;

    public void setDaerahRawanBanjirDao(DaerahRawanBanjirDao daerahRawanBanjirDao) {
        this.daerahRawanBanjirDao = daerahRawanBanjirDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public DaerahRawanBanjir findById(Long id) {
        return daerahRawanBanjirDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_DAERAH_RAWAN_BANJIR, id, os);
    }

    @Override
    public List<DaerahRawanBanjir> findByDas(Das das) {
        return daerahRawanBanjirDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "name", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return daerahRawanBanjirDao.countAlls();
    }

    @Override
    public List<DaerahRawanBanjir> findAlls(Long start, Long count) {
        return daerahRawanBanjirDao.findAlls(start, count, "description", OrderDir.ASC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return daerahRawanBanjirDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<DaerahRawanBanjir> findByKeyword(String keyword, Long start, Long count) {
        return daerahRawanBanjirDao.findByFilter(createKeywordFilter(keyword), start, count, "description", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            daerahRawanBanjirDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_DAERAH_RAWAN_BANJIR, id);
        }
    }

    @Override
    public void save(DaerahRawanBanjir daerahRawanBanjir) {
        daerahRawanBanjirDao.save(daerahRawanBanjir);
    }

    @Override
    public void save(DaerahRawanBanjir drb, InputStream inputStream) throws IOException {
        daerahRawanBanjirDao.save(drb);
        blobRepository.store(BlobDataType.SDA_DAERAH_RAWAN_BANJIR, drb.getId(), inputStream);
    }
}
