package com.integrasolusi.pusda.sisda.service.patternplanning;

import com.integrasolusi.pusda.sisda.dao.patternplanning.PolaRencanaPsdadaFileDao;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFile;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;
import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/19/11
 * Time         : 6:27 PM
 */
public class PolaRencanaPsdaFileServiceImpl implements PolaRencanaPsdaFileService {
    private PolaRencanaPsdadaFileDao polaRencanaPsdadaFileDao;
    private BlobRepository blobRepository;

    public void setPolaRencanaPsdadaFileDao(PolaRencanaPsdadaFileDao polaRencanaPsdadaFileDao) {
        this.polaRencanaPsdadaFileDao = polaRencanaPsdadaFileDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createKeywordFilter(keyword));
        return filter;
    }

    @Override
    public Long countByKeyword(String keyword) {
        return polaRencanaPsdadaFileDao.countByFilter(createPublishedAndKeywordFilter(keyword));
    }


    @Override
    public PolaRencanaPsdaFile findById(Long id) {
        return polaRencanaPsdadaFileDao.findById(id);
    }

    @Override
    public void copyContent(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.PATTERN_AND_PLAN_SDA, id, os);
    }

    @Override
    public Long countAlls() {
        return polaRencanaPsdadaFileDao.countAlls();
    }

    @Override
    public List<PolaRencanaPsdaFile> findAlls(Long start, Long count) {
        return polaRencanaPsdadaFileDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public List<PolaRencanaPsdaFile> findByKeyword(String keyword, Long start, Long count) {
        return polaRencanaPsdadaFileDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void removeById(Long id) {
        polaRencanaPsdadaFileDao.removeById(id);
        blobRepository.remove(BlobDataType.PATTERN_AND_PLAN_SDA, id);
    }

    @Override
    public void save(PolaRencanaPsdaFile polaRencanaPsdaFile, InputStream is) throws IOException {
        polaRencanaPsdadaFileDao.save(polaRencanaPsdaFile);
        blobRepository.store(BlobDataType.PATTERN_AND_PLAN_SDA, polaRencanaPsdaFile.getId(), is);
    }

    @Override
    public void save(PolaRencanaPsdaFile polaRencanaPsdaFile) {
        polaRencanaPsdadaFileDao.save(polaRencanaPsdaFile);
    }

    @Override
    public List<PolaRencanaPsdaFile> findByFolder(PolaRencanaPsdaFolder folder) {
        return polaRencanaPsdadaFileDao.findByFilter(new ValueFilter("folder", QueryOperator.EQUALS, folder, "folder"), "index", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            polaRencanaPsdadaFileDao.removeById(id);
            blobRepository.remove(BlobDataType.PATTERN_AND_PLAN_SDA, id);
        }
    }

}
