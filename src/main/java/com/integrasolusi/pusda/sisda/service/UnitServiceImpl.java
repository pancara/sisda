package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.UnitDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Unit;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.pusda.sisda.web.controller.map.OrganizationType;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 1:28 PM
 */
public class UnitServiceImpl implements UnitService {
    private static final Long UNIT_ID_STRUKTURAL = 1L;
    private static final Long UNIT_ID_FUNGSIONAL = 2L;

    private UnitDao unitDao;
    private BlobRepository blobRepository;

    public void setUnitDao(UnitDao unitDao) {
        this.unitDao = unitDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Unit findRoot(OrganizationType type) {
        Long id = OrganizationType.struktural.equals(type) ? UNIT_ID_STRUKTURAL : UNIT_ID_FUNGSIONAL;
        return unitDao.findById(id);
    }

    @Override
    public List<Unit> findByParent(Unit parent) {
        return unitDao.findByFilter(new ValueFilter("parent", QueryOperator.EQUALS, parent), "index", OrderDir.ASC);
    }

    @Override
    public Long getChildCount(Unit unit) {
        return unitDao.countByFilter(new ValueFilter("parent", QueryOperator.EQUALS, unit));
    }

    @Override
    public Unit findById(Long id) {
        return unitDao.findById(id);
    }

    @Override
    public void save(Unit unit) {
        if (unit.getId() == null) {
            Unit last = unitDao.findUniqueByFilter(new ValueFilter("parent", QueryOperator.EQUALS, unit.getParent()), "index", OrderDir.DESC);
            Long newIndex = last.getIndex() + 1;
        }
        unitDao.save(unit);
    }

    @Override
    public void removeById(Long id) {
        Unit unit = unitDao.findById(id);
        Unit parent = unit.getParent();

        java.util.List<Unit> children = unitDao.findByFilter(new ValueFilter("parent", QueryOperator.EQUALS, unit));
        for (Unit child : children) {
            child.setParent(parent);
            unitDao.save(child);
        }
        unitDao.remove(unit);
    }

    @Override
    public boolean moveDown(Long id) {
        Unit current = unitDao.findById(id);
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("parent", QueryOperator.EQUALS, current.getParent()));
        filter.add(new ValueFilter("index", QueryOperator.GREATER, current.getIndex()));

        Unit next = unitDao.findUniqueByFilter(filter, "index", OrderDir.ASC);
        if (next == null) {
            return false;
        }

        Long currentIndex = current.getIndex();
        current.setIndex(next.getIndex());
        unitDao.save(current);

        next.setIndex(currentIndex);
        unitDao.save(next);
        return true;
    }

    @Override
    public boolean moveUp(Long id) {
        Unit current = unitDao.findById(id);
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("parent", QueryOperator.EQUALS, current.getParent()));
        filter.add(new ValueFilter("index", QueryOperator.LESS, current.getIndex()));

        Unit previous = unitDao.findUniqueByFilter(filter, "index", OrderDir.DESC);
        if (previous == null)
            return false;

        Long currentIndex = current.getIndex();
        current.setIndex(previous.getIndex());
        previous.setIndex(currentIndex);
        unitDao.save(current);
        unitDao.save(previous);
        return true;
    }

    @Override
    public void normalizeChildrenIndex(Unit parent) {
        if (unitDao.existSameIndex(parent)) {
            List<Unit> children = unitDao.findByFilter(new ValueFilter("parent", QueryOperator.EQUALS, parent), "index", OrderDir.ASC);
            Long index = 1L;
            for (Unit unit : children) {
                unit.setIndex(index);
                unitDao.save(unit);
                index++;
            }
        }
    }
}
