package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Unit;
import com.integrasolusi.pusda.sisda.web.controller.map.OrganizationType;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 1:28 PM
 */
public interface UnitService {

    Unit findRoot(OrganizationType type);

    List<Unit> findByParent(Unit parent);

    Long getChildCount(Unit unit);

    Unit findById(Long id);

    void save(Unit unit);

    void removeById(Long id);

    boolean moveUp(Long id);

    boolean moveDown(Long id);

    void normalizeChildrenIndex(Unit unit);
}
