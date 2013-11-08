package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.Slide;

/**
 * Programmer   : pancara
 * Date         : 6/21/11
 * Time         : 12:14 PM
 */
public interface SlideDao extends GenericDao<Slide, Long> {
    Long getMaxIndex();
}
