package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;

import java.util.List;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:18 PM
 */
public interface RegulationFolderService {
    void save(Folder folder);

    List<Folder> findAlls();

    List<Folder> findByParent(Folder parent);

    Folder findById(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    List<Folder> findAlls(Long start, Long count);

    List<Folder> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void moveDown(Folder folder);

    void moveUp(Folder folder);

    void reindex();
}
