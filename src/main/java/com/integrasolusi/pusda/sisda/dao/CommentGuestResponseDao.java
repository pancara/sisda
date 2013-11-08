package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 3:02 PM
 */
public interface CommentGuestResponseDao extends GenericDao<CommentGuestResponse, Long> {
    CommentGuest getComment(CommentGuestResponse response);
}
