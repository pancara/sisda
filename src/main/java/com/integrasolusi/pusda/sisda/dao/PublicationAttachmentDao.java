package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 1:02 PM
 */
public interface PublicationAttachmentDao extends GenericDao<PublicationAttachment, Long> {
    Integer getMaxIndexByPublication(Publication publication);
}
