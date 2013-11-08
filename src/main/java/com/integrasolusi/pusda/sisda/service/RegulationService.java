package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.regulation.Regulation;
import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/19/11
 * Time         : 6:26 PM
 */
public interface RegulationService {
    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<Regulation> getPublished(Long start, Long count);

    List<Regulation> getPublishedByKeyword(String keyword, Long start, Long count);

    Regulation findById(Long id);

    void copyContent(Long id, OutputStream os) throws IOException;

    Long countAlls();

    Long countByKeyword(String keyword);

    List<Regulation> findAlls(Long start, Long count);

    List<Regulation> findByKeyword(String keyword, Long start, Long count);

    void removeById(Long id);

    void save(Regulation regulation, InputStream is) throws IOException;

    void save(Regulation regulation);

    List<Regulation> findByFolder(Folder folder);

    void removeByIds(Long[] ids);
}
