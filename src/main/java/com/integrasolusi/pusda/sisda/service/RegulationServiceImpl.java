package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.RegulationDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.regulation.Regulation;
import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/19/11
 * Time         : 6:27 PM
 */
public class RegulationServiceImpl implements RegulationService {
    private RegulationDao regulationDao;
    private BlobRepository blobRepository;

    public void setRegulationDao(RegulationDao regulationDao) {
        this.regulationDao = regulationDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    private ValueFilter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true);
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createKeywordFilter(keyword));
        filter.add(createPublishedFilter());
        return filter;
    }

    @Override
    public Long countPublished() {
        return regulationDao.countByFilter(createPublishedFilter());
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        return regulationDao.countByFilter(createPublishedAndKeywordFilter(keyword));
    }

    @Override
    public List<Regulation> getPublished(Long start, Long count) {
        return regulationDao.findByFilter(createPublishedFilter(), start, count);
    }

    @Override
    public List<Regulation> getPublishedByKeyword(String keyword, Long start, Long count) {
        return regulationDao.findByFilter(createKeywordFilter(keyword), start, count);
    }

    @Override
    public Regulation findById(Long id) {
        return regulationDao.findById(id);
    }

    @Override
    public void copyContent(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.REGULATION, id, os);
    }

    @Override
    public Long countAlls() {
        return regulationDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return regulationDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<Regulation> findAlls(Long start, Long count) {
        return regulationDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public List<Regulation> findByKeyword(String keyword, Long start, Long count) {
        return regulationDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void removeById(Long id) {
        regulationDao.removeById(id);
        blobRepository.remove(BlobDataType.REGULATION, id);
    }

    @Override
    public void save(Regulation regulation, InputStream is) throws IOException {
        regulationDao.save(regulation);
        blobRepository.store(BlobDataType.REGULATION, regulation.getId(), is);
    }

    @Override
    public void save(Regulation regulation) {
        regulationDao.save(regulation);
    }

    @Override
    public List<Regulation> findByFolder(Folder folder) {
        return regulationDao.findByFilter(new ValueFilter("folder", QueryOperator.EQUALS, folder, "folder"), "index", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for(Long id : ids) {
            regulationDao.removeById(id);
            blobRepository.remove(BlobDataType.REGULATION, id);
        }
    }
}
