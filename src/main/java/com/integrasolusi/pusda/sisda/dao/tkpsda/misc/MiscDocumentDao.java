package com.integrasolusi.pusda.sisda.dao.tkpsda.misc;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscDocument;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;
import com.integrasolusi.query.generic.GenericDao;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:27 PM
 */
public interface MiscDocumentDao extends GenericDao<MiscDocument, Long> {

    Integer getMaxIndex(MiscFolder folder);
    
}
