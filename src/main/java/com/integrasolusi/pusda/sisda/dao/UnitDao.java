package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.Unit;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 1:26 PM
 */
public interface UnitDao extends GenericDao<Unit, Long> {

    boolean existSameIndex(Unit parent);
}
