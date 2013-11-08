package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Leaflet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 10:45 AM
 */
public interface LeafletService {
    List<Leaflet> getLatest(Long count);

    Leaflet findById(Long id);

    void getThumbfileBlob(Long id, OutputStream outputStream) throws IOException;
    
    void getThumbfileBlob(Long id, OutputStream outputStream, Integer width, Integer height) throws Exception;

    void getDocumentBlob(Long id, OutputStream outputStream) throws IOException;

    Long countAlls();

    Long countByKeyword(String keyword);

    List<Leaflet> get(Long start, Long count);

    List<Leaflet> getByKeyword(String keyword, Long start, Long count);

    void save(Leaflet leaflet);

    void save(Leaflet leaflet, InputStream inputStream) throws IOException;

    void removeById(Long id);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    void removeThumb(Long id);

    void saveThumb(Leaflet leaflet, InputStream inputStream) throws IOException;

    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<Leaflet> getPublished(Long start, Long count);

    List<Leaflet> getPublishedByKeyword(String keyword, Long start, Long count);

    Boolean hasPicture(Long id);

    
}
