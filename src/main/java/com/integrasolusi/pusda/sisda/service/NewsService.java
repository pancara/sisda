package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentNews;
import com.integrasolusi.pusda.sisda.persistence.News;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:02 PM
 */
public interface NewsService {
    Long countAlls();

    Long countByKeyword(String keyword);

    java.util.List<News> get(Long start, Long count);

    java.util.List<News> getByKeyword(String keyword, Long start, Long count);

    void save(News news);

    void save(News news, InputStream is) throws IOException;

    News findById(Long id);

    void removeById(Long id);

    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<News> getPublished(Long start, Long count);

    List<News> getPublishedByKeyword(String keyword, Long start, Long count);

    Boolean hasPicture(Long id);

    BufferedImage getTitlePicture(Long id) throws IOException;

    void saveComment(CommentNews comment);

    List<CommentNews> getComments(Long newsId);

    List<News> getLatest(Long count);

    List<News> getLatest();

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    void getHeadlinePictureBlob(Long id, OutputStream outputStream) throws IOException;

    void getHeadlinePictureBlob(Long id, OutputStream outputStream, Integer width, Integer heigth) throws Exception;

    void removePhoto(Long id) throws IOException;
}
