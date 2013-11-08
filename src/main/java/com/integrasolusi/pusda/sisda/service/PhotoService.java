package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 2:11 PM
 */
public interface PhotoService {
    void save(Photo photo);

    void save(Photo photo, InputStream is) throws IOException;

    void removeById(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    List<Photo> findAlls(Long start, Long count);

    List<Photo> findByKeyword(String keyword, Long start, Long count);

    Photo findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException;

    void removeByIds(Long[] ids) throws IOException;

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);
}
