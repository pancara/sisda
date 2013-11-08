package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentPublication;
import com.integrasolusi.pusda.sisda.persistence.Publication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:02 PM
 */
public interface PublicationService {
    Long countAlls();

    Long countByKeyword(String keyword);

    List<Publication> get(Long start, Long count);

    List<Publication> getByKeyword(String keyword, Long start, Long count);

    void save(Publication publication);

    void save(Publication publication, InputStream inputStream) throws IOException;

    Publication findById(Long id);

    void removeById(Long id);

    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<Publication> getPublished(Long start, Long count);

    List<Publication> getPublishedByKeyword(String keyword, Long start, Long count);

    Boolean hasTitlePicture(Long id);

    void savePicture(Long id, InputStream is) throws IOException;

    void saveComment(CommentPublication comment);

    List<CommentPublication> getComments(Long publicationId);

    List<Publication> getLatest();

    List<Publication> getLatest(Long count);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    void removePicture(Long id);

    void getPicture(Long id, OutputStream outputStream) throws Exception;

    void getPicture(Long id, OutputStream outputStream, Integer width, Integer height) throws Exception;
}
