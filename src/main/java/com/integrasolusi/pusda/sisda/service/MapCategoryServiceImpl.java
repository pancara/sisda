package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.MapCategoryDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;

import java.util.LinkedList;
import java.util.List;

/**
 * User: pancara
 * Date: 9/29/12
 * Time: 11:16 AM
 */
public class MapCategoryServiceImpl implements MapCategoryService {
    private MapCategoryDao mapCategoryDao;

    public void setMapCategoryDao(MapCategoryDao mapCategoryDao) {
        this.mapCategoryDao = mapCategoryDao;
    }

    @Override
    public List<MapCategory> findAlls() {
        return mapCategoryDao.findAlls("id", OrderDir.ASC);
    }


    @Override
    public List<MapCategory> findAlls(String orderProperty) {
        return mapCategoryDao.findAlls(orderProperty, OrderDir.ASC);
    }

    @Override
    public List<MapCategory> findLeafs(String orderProperty) {
        List<MapCategory> categories = mapCategoryDao.findAlls(orderProperty, OrderDir.ASC);
        List<MapCategory> leafs = new LinkedList<>();

        for (MapCategory category : categories) {
            Long childCount = mapCategoryDao.countByFilter(new ValueFilter("parent", QueryOperator.EQUALS, category, "parent"));
            if (childCount.intValue() == 0) {
                leafs.add(category);
            }
        }
        return leafs;
    }

    @Override
    public void save(MapCategory category) {
        mapCategoryDao.save(category);
    }

    @Override
    public MapCategory findById(Long id) {
        return mapCategoryDao.findById(id);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            mapCategoryDao.removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            MapCategory category = mapCategoryDao.findById(id);
            category.setPublished(true);
            mapCategoryDao.save(category);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            MapCategory category = mapCategoryDao.findById(id);
            category.setPublished(false);
            mapCategoryDao.save(category);
        }
    }

    @Override
    public boolean isCircular(Long categoryId, Long parentId) {
        if (categoryId == null || parentId == null)
            return false;
        MapCategory parent = mapCategoryDao.findById(parentId);

        while (true) {
            if (parent.getParent() == null)
                return false;


            if (parent.getId().equals(categoryId))
                return true;


            parent = parent.getParent();
        }

    }

    @Override
    public List<MapCategory> findByParent(MapCategory parent, String orderProperty) {
        return mapCategoryDao.findByFilter(new ValueFilter("parent", QueryOperator.EQUALS, parent, "parent"), orderProperty, OrderDir.ASC);
    }

    @Override
    public Long childCount(MapCategory category) {
        return mapCategoryDao.countByFilter(new ValueFilter("parent", QueryOperator.EQUALS, category, "parent"));
    }

}
