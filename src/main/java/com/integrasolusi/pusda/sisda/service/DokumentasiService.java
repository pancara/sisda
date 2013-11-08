package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/20/11
 * Time         : 4:29 PM
 */
public interface DokumentasiService {
    Dokumentasi findById(Long id);

    Long countAlls();

    Long countPublished();

    List<Dokumentasi> get(Long start, Long count);

    List<Dokumentasi> getPublished(Long start, Long rowPerPage);

    void saveTitlePicture(Long galleryId, java.io.InputStream is) throws IOException;

    InputStream getTitlePictureStream(Long galleryId) throws IOException;

    Boolean hasTitlePicture(Long galleryId);

    void save(Dokumentasi gallery);

    void removeById(Long galleryId);

    void savePicture(DokumentasiPhoto picture, InputStream inputStream) throws IOException;

    void removePictureById(Long galleryPictureId);

    Long countByKeyword(String keyword);

    List<Dokumentasi> findAlls(Long start, Long count);

    List<Dokumentasi> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);
}
