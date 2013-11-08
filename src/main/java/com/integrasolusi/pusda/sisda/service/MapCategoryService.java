package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.MapCategory;

import java.util.List;

/**
 * User: pancara
 * Date: 9/29/12
 * Time: 11:16 AM
 */
public interface MapCategoryService {
    List<MapCategory> findAlls();

    List<MapCategory> findAlls(String orderProperty);

    List<MapCategory> findLeafs(String orderProperty);

    void save(MapCategory category);

    MapCategory findById(Long id);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    boolean isCircular(Long categoryId, Long parentId);

    List<MapCategory> findByParent(MapCategory parent, String orderProperty);
    
    Long childCount(MapCategory category);
}
