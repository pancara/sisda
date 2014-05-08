package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;
import com.integrasolusi.query.filter.ValueFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 4:30 PM
 */
public interface MapService {
    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<Map> getPublished(Long start, Long count);

    List<Map> getPublishedByKeyword(String keyword, Long start, Long count);

    Map findById(Long id);

    void getDocument(Long id, java.io.OutputStream outputStream) throws IOException;

    void getBlob(Long id, Integer w, Integer h, OutputStream os, String format) throws IOException;

    Boolean hasPicture(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countByCategory(Long category);

    Long countByCategoryAndKeyword(Long category, String keyword);

    Long countByPublished(boolean published);

    Long countByKeywordAndPublished(String keyword, boolean published);

    Long countByCategoryAndPublished(Long category, boolean published);

    Long countByCategoryAndKeywordAndPublished(Long category, String keyword, boolean published);

    List<Map> get(Long start, Long count);

    List<Map> getByKeyword(String keyword, Long start, Long count);

    void removeById(Long id);

    void save(Map map, InputStream is) throws IOException;

    void save(Map map);

    List<Map> findAlls(Long start, Long count);

    List<Map> findByFilter(ValueFilter filter);

    List<Map> findByKeyword(String keyword, Long start, Long count);

    List<Map> findByCategory(MapCategory category);

    List<Map> findByCategory(Long category, Long start, Long count);

    List<Map> findByCategoryAndKeyword(Long category, String keyword, Long start, Long count);

    List<Map> findByPublished(Long start, Long count, boolean published);

    List<Map> findByKeywordAndPublished(String keyword, boolean published, Long start, Long count);

    List<Map> findByCategoryAndPublished(Long category, boolean published, Long start, Long count);

    List<Map> findByCategoryAndKeywordAndPublished(Long category, String keyword, boolean published, Long start, Long count);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);


}