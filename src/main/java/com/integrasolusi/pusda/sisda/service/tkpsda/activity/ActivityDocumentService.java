package com.integrasolusi.pusda.sisda.service.tkpsda.activity;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.ActivityDocument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:31 PM
 */
public interface ActivityDocumentService {

    List<ActivityDocument> findByActivity(Activity activity);

    ActivityDocument findById(Long id);

    void save(ActivityDocument document);

    void save(ActivityDocument document, InputStream is) throws IOException;

    void removeByIds(Long[] ids);

    void getBlob(Long id, OutputStream os) throws IOException;
}
