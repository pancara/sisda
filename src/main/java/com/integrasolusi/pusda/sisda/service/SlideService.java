package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Slide;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/10/11
 * Time         : 2:47 PM
 */
public interface SlideService {

    List<Slide> getPublished();

    Slide getNext(Long currentId);

    void reindexAlls();

    Slide findById(Long id);

    Slide getRandom();

    BufferedImage getPicture(Long id) throws IOException;

    Long countAlls();

    List<Slide> get(Long start, Long count);

    void removeById(Long id);

    void save(Slide slide, InputStream inputStream) throws IOException;

    void save(Slide slide) throws IOException;

    Long countByKeyword(String keyword);

    List<Slide> findAlls(Long start, Long count);

    List<Slide> findByKeyword(String keyword, Long start, Long count);

    void getBlob(Long id, OutputStream os) throws IOException;

    void getBlob(Long id, Integer width, Integer height, OutputStream os) throws IOException;

    void removeByIds(Long[] ids) throws IOException;

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);
}
