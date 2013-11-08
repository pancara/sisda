package com.integrasolusi.pusda.sisda.service.sda.bendung;

import com.integrasolusi.pusda.sisda.dao.sda.bendung.BendungDao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.Bendung;
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
 * Date: 7/31/12
 * Time: 2:48 PM
 */
public class BendungServiceImpl implements BendungService {

    private BendungDao bendungDao;
    private BlobRepository blobRepository;

    public void setBendungDao(BendungDao bendungDao) {
        this.bendungDao = bendungDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<Bendung> findAlls() {
        return bendungDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public Bendung findById(Long id) {
        return bendungDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_BENDUNG, id, os);
    }

    @Override
    public Long countAlls() {
        return bendungDao.countAlls();
    }

    @Override
    public List<Bendung> findAlls(Long start, Long count) {
        return bendungDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return bendungDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<Bendung> findByKeyword(String keyword, Long start, Long count) {
        return bendungDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            bendungDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_BENDUNG, id);
        }
    }

    @Override
    public void save(Bendung bendung) {
        bendungDao.save(bendung);
    }

    @Override
    public void save(Bendung bendung, InputStream inputStream) throws IOException {
        bendungDao.save(bendung);
        blobRepository.store(BlobDataType.SDA_BENDUNG, bendung.getId(), inputStream);
    }

    @Override
    public List<Bendung> findByDas(Das das) {
        return bendungDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "name", OrderDir.ASC);
    }

    @Override
    public List<Bendung> findByWs(WilayahSungai ws) {
        return bendungDao.findByFilter(new ValueFilter("das.wilayahSungai", QueryOperator.EQUALS, ws, "das_ws"), "name", OrderDir.ASC);
    }

}
