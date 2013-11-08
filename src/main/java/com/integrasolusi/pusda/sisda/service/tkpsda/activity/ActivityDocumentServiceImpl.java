package com.integrasolusi.pusda.sisda.service.tkpsda.activity;

import com.integrasolusi.pusda.sisda.dao.tkpsda.meeting.ActivityDocumentDao;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.ActivityDocument;
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
public class ActivityDocumentServiceImpl implements ActivityDocumentService {
    private ActivityDocumentDao activityDocumentDao;
    private BlobRepository blobRepository;

    public void setActivityDocumentDao(ActivityDocumentDao activityDocumentDao) {
        this.activityDocumentDao = activityDocumentDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<ActivityDocument> findByActivity(Activity activity) {
        return activityDocumentDao.findByFilter(new ValueFilter("activity", QueryOperator.EQUALS, activity, "activity"), "index", OrderDir.ASC);
    }

    @Override
    public ActivityDocument findById(Long id) {
        return activityDocumentDao.findById(id);
    }

    @Override
    public void save(ActivityDocument document) {
        chechIndex(document);
        activityDocumentDao.save(document);
    }

    @Override
    public void save(ActivityDocument document, InputStream is) throws IOException {
        chechIndex(document);
        activityDocumentDao.save(document);
        blobRepository.store(BlobDataType.TKPSDA_ACTIVITY_DOCUMENT, document.getId(), is);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            activityDocumentDao.removeById(id);
        }

        for (Long id : ids) {
            blobRepository.remove(BlobDataType.TKPSDA_ACTIVITY_DOCUMENT, id);
        }
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.TKPSDA_ACTIVITY_DOCUMENT, id, os);
    }

    private void chechIndex(ActivityDocument document) {
        if (document.getIndex() == null) {
            Integer maxIndex = activityDocumentDao.getMaxIndex(document.getActivity());
            document.setIndex(maxIndex + 1);
        }
    }
}
