package com.integrasolusi.pusda.sisda.dao.tkpsda.meeting;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.ActivityDocument;
import com.integrasolusi.query.generic.GenericDao;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:27 PM
 */
public interface ActivityDocumentDao extends GenericDao<ActivityDocument, Long> {
    Integer getMaxIndex(Activity activity);
}
