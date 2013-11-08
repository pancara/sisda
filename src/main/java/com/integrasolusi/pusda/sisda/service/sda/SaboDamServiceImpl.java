package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.SaboDamDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:55 AM
 */
public class SaboDamServiceImpl implements SaboDamService {
    private SaboDamDao saboDamDao;
    private BlobRepository blobRepository;

    public void setSaboDamDao(SaboDamDao saboDamDao) {
        this.saboDamDao = saboDamDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<SaboDam> findAlls() {
        return saboDamDao.findAlls("code", OrderDir.ASC);
    }

    @Override
    public SaboDam findById(Long id) {
        return saboDamDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_SABO_DAM, id, os);
    }

    @Override
    public List<SaboDam> findBySungai(SungaiSaboDam sungai) {
        return saboDamDao.findByFilter(new ValueFilter("sungai", QueryOperator.EQUALS, sungai, "sungai"), "code", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return saboDamDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return saboDamDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<SaboDam> findByKeyword(String keyword, Long start, Long count) {
        return saboDamDao.findByFilter(createKeywordFilter(keyword), start, count, "code", OrderDir.ASC);
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("code", QueryOperator.LIKE, keyword, "code");
    }

    @Override
    public List<SaboDam> findAlls(Long start, Long count) {
        return saboDamDao.findAlls(start, count, "code", OrderDir.ASC);
    }

    @Override
    public Long countBySungai(Long sungai) {
        return saboDamDao.countByFilter(createSungaiFilter(sungai));
    }

    private ValueFilter createSungaiFilter(Long sungai) {
        return new ValueFilter("sungai.id", QueryOperator.EQUALS, sungai, "sungai_id");
    }

    @Override
    public Long countBySungaiAndKeyword(Long sungai, String keyword) {
        return saboDamDao.countByFilter(createSungaiAndKeywordFilter(sungai, keyword));
    }

    private CompositeFilter createSungaiAndKeywordFilter(Long sungai, String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createSungaiFilter(sungai));
        filter.add(createKeywordFilter(keyword));
        return filter;
    }

    @Override
    public List<SaboDam> findBySungai(Long sungai, Long start, Long count) {
        return saboDamDao.findByFilter(createSungaiFilter(sungai), start, count, "code", OrderDir.ASC);
    }

    @Override
    public List<SaboDam> findBySungaiAndKeyword(Long sungai, String keyword, Long start, Long count) {
        return saboDamDao.findByFilter(createSungaiAndKeywordFilter(sungai, keyword), start, count, "code", OrderDir.ASC);
    }

    @Override
    public void save(SaboDam sabodam) {
        saboDamDao.save(sabodam);
    }

    @Override
    public void save(SaboDam sabodam, InputStream inputStream) throws IOException {
        saboDamDao.save(sabodam);
        blobRepository.store(BlobDataType.SDA_SABO_DAM, sabodam.getId(), inputStream);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            saboDamDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_SABO_DAM, id);
        }
    }

    @Override
    public boolean hasDocumentBlob(Long id) {
        return blobRepository.isExist(BlobDataType.SDA_SABO_DAM, id);
    }
}
