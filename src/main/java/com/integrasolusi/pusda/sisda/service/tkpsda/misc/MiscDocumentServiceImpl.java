package com.integrasolusi.pusda.sisda.service.tkpsda.misc;

import com.integrasolusi.pusda.sisda.dao.tkpsda.misc.MiscDocumentDao;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscDocument;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:31 PM
 */
public class MiscDocumentServiceImpl implements MiscDocumentService {

    private MiscDocumentDao miscDocumentDao;
    private BlobRepository blobRepository;

    public void setMiscDocumentDao(MiscDocumentDao miscDocumentDao) {
        this.miscDocumentDao = miscDocumentDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<MiscDocument> findByFolder(MiscFolder folder) {
        return miscDocumentDao.findByFilter(new ValueFilter("folder", QueryOperator.EQUALS, folder, "folder"), "index", OrderDir.ASC);
    }

    @Override
    public MiscDocument findById(Long id) {
        return miscDocumentDao.findById(id);
    }

    @Override
    public void save(MiscDocument document) {
        chekIndex(document);
        miscDocumentDao.save(document);
    }

    @Override
    public void save(MiscDocument document, InputStream is) throws IOException {
        chekIndex(document);
        miscDocumentDao.save(document);
        blobRepository.store(BlobDataType.TKPSDA_MISC_DOCUMENT, document.getId(), is);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            miscDocumentDao.removeById(id);
        }

        for (Long id : ids) {
            blobRepository.remove(BlobDataType.TKPSDA_MISC_DOCUMENT, id);
        }
    }

    @Override
    public void getBlob(Long documentId, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.TKPSDA_MISC_DOCUMENT, documentId, os);
    }

    private void chekIndex(MiscDocument document) {
        if (document.getIndex() == null) {
            Integer maxIndex = miscDocumentDao.getMaxIndex(document.getFolder());
            document.setIndex(maxIndex + 1);
        }
    }
}
