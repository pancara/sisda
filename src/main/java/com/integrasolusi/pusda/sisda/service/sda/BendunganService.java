package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.sda.Bendungan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:38 PM
 */
public interface BendunganService {
    Bendungan findById(Long id);

    List<Bendungan> findAlls();

    void getThumbPhoto(Long id, OutputStream outputStream) throws IOException;

    void removeById(Long id);

    void save(Bendungan bendungan);

    void save(Bendungan bendungan, InputStream is) throws IOException;
}
