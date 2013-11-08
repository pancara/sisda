package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.KlimatologiDao;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.Klimatologi;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosKlimatologi;
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
 * Date: 8/3/12
 * Time: 8:49 PM
 */
public class KlimatologiServiceImpl implements KlimatologiService {
    private KlimatologiDao klimatologiDao;
    private BlobRepository blobRepository;

    public void setKlimatologiDao(KlimatologiDao klimatologiDao) {
        this.klimatologiDao = klimatologiDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Klimatologi findById(Long id) {
        return klimatologiDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_KLIMATOLOGI, id, os);

    }

    @Override
    public Long countAlls() {
        return klimatologiDao.countAlls();
    }

    @Override
    public List<Klimatologi> findAlls(Long start, Long count) {
        return klimatologiDao.findAlls(start, count);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return klimatologiDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public List<Klimatologi> findByKeyword(String keyword, Long start, Long count) {
        return klimatologiDao.findByFilter(createKeywordFilter(keyword), start, count, "description", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            klimatologiDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_KLIMATOLOGI, id);
        }
    }

    @Override
    public void save(Klimatologi klimatologi) {
        klimatologiDao.save(klimatologi);
    }

    @Override
    public void save(Klimatologi klimatologi, InputStream inputStream) throws IOException {
        klimatologiDao.save(klimatologi);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_KLIMATOLOGI, klimatologi.getId(), inputStream);
    }

    @Override
    public List<Klimatologi> findByPos(PosKlimatologi pos) {
        return klimatologiDao.findByFilter(new ValueFilter("pos", QueryOperator.EQUALS, pos, "pos"), "description", OrderDir.ASC);

    }

}
