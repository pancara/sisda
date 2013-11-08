package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.StrukturOrganisasi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/27/12
 * Time       : 2:55 PM
 */
public interface StrukturOrganisasiService {
    StrukturOrganisasi findById(Long id);

    List<StrukturOrganisasi> findAlls();

    void getBlob(Long id, OutputStream outputStream) throws IOException;

    void getBlob(Long id, Integer width, Integer height, OutputStream outputStream) throws IOException;

    void save(StrukturOrganisasi so);

    void save(StrukturOrganisasi so, InputStream inputStream) throws IOException;
}
