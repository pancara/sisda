package com.integrasolusi.pusda.sisda.service.patternplanning;

import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/19/11
 * Time         : 6:26 PM
 */
public interface PolaRencanaPsdaFolderService {
    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<PolaRencanaPsdaFolder> getPublished(Long start, Long count);

    List<PolaRencanaPsdaFolder> getPublishedByKeyword(String keyword, Long start, Long count);

    PolaRencanaPsdaFolder findById(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    List<PolaRencanaPsdaFolder> findAlls(Long start, Long count);

    List<PolaRencanaPsdaFolder> findByKeyword(String keyword, Long start, Long count);

    void removeById(Long id);

    void save(PolaRencanaPsdaFolder folder);

    List<PolaRencanaPsdaFolder> findByParent(PolaRencanaPsdaFolder folder);

    void removeByIds(Long[] ids);

    void moveDown(PolaRencanaPsdaFolder folder);

    void moveUp(PolaRencanaPsdaFolder folder);

    void reindex();
}
