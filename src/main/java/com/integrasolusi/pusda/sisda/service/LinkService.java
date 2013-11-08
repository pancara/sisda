package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Link;
import com.integrasolusi.pusda.sisda.persistence.LinkType;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/27/11
 * Time         : 5:46 PM
 */
public interface LinkService {
    void save(Link link);

    void save(Link link, java.io.InputStream is) throws IOException;

    void removeById(Long id);

    List<Link> findByTypeAndPublished(Long type);

    Long countAlls();

    List<Link> get(Long start, Long count);

    void savePicture(Long id, InputStream is) throws IOException;

    Link findById(Long id);

    BufferedImage getPicture(Long id) throws IOException;

    LinkType getTypeEntity(Long typeId);

    List<LinkType> findAvailableTypes();

    void getBlob(Long id, OutputStream outputStream) throws IOException;

    List<Link> findAlls();

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);
}
