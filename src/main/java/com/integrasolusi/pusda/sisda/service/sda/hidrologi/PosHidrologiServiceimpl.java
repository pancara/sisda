package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.dao.sda.hidrologi.PosHidrologiDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosHidrologi;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import org.apache.commons.lang.BooleanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 7:15 PM
 */
public class PosHidrologiServiceimpl implements PosHidrologiService {
    private PosHidrologiDao posHidrologiDao;
    private BlobRepository blobRepository;

    public void setPosHidrologiDao(PosHidrologiDao posHidrologiDao) {
        this.posHidrologiDao = posHidrologiDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public PosHidrologi findById(Long id) {
        return posHidrologiDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_HIDROLOGI_SEBARAN_POS, id, os);
    }

    @Override
    public List<PosHidrologi> findByDas(Das das) {
        return posHidrologiDao.findByFilter(new ValueFilter("das", QueryOperator.EQUALS, das, "das"), "description", OrderDir.ASC);
    }

    @Override
    public List<PosHidrologi> findAlls() {
        return posHidrologiDao.findAlls("description", OrderDir.ASC);
    }

    @Override
    public List<PosHidrologi> findAlls(Long start, Long count) {
        return posHidrologiDao.findAlls(start, count, "description", OrderDir.ASC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return posHidrologiDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<PosHidrologi> findByKeyword(String keyword, Long start, Long count) {
        return posHidrologiDao.findByFilter(createKeywordFilter(keyword), start, count, "description", OrderDir.ASC);
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("description", QueryOperator.LIKE, keyword, "description");
    }

    @Override
    public Long countAlls() {
        return posHidrologiDao.countAlls();
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            posHidrologiDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_HIDROLOGI_SEBARAN_POS, id);
        }
    }

    @Override
    public void save(PosHidrologi posHidrologi) {
        posHidrologiDao.save(posHidrologi);
        if (BooleanUtils.isTrue(posHidrologi.getActive())) {
            posHidrologiDao.deactivateOthers(posHidrologi);
        }
    }

    @Override
    public void save(PosHidrologi posHidrologi, InputStream inputStream) throws IOException {
        save(posHidrologi);
        blobRepository.store(BlobDataType.SDA_HIDROLOGI_SEBARAN_POS, posHidrologi.getId(), inputStream);
    }

    @Override
    public void activateById(Long id) {
        PosHidrologi posHidrologi = posHidrologiDao.findById(id);
        posHidrologi.setActive(true);
        posHidrologiDao.save(posHidrologi);

        posHidrologiDao.deactivateOthers(posHidrologi);
    }

    @Override
    public PosHidrologi findActive() {
        return posHidrologiDao.findUniqueByFilter(new ValueFilter("active", QueryOperator.EQUALS, true, "active"));
    }

    @Override
    public PosHidrologi findFirst() {
        return posHidrologiDao.findByIndex(0L, "id", OrderDir.ASC);
    }
}
