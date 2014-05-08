package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 10/4/12
 * Time: 4:42 PM
 */
public interface DokumentasiPhotoService {
    DokumentasiPhoto findById(Long id);

    List<DokumentasiPhoto> findByDokumentasi(Dokumentasi dokumentasi);

    List<DokumentasiPhoto> findByDokumentasi(Dokumentasi dokumentasi, Long start, Long count);

    void getBlob(Long id, OutputStream os) throws IOException;

    void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException;

    void save(DokumentasiPhoto photo);

    void save(DokumentasiPhoto picture, InputStream is) throws IOException;

    void removeByIds(Long[] ids);
}
