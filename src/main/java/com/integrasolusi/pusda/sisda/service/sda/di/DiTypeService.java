package com.integrasolusi.pusda.sisda.service.sda.di;

import com.integrasolusi.pusda.sisda.persistence.sda.di.DiType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 4:31 PM
 */
public interface DiTypeService {
    List<DiType> findAlls();

    List<DiType> findAlls(Long start, Long count);

    DiType findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    Long countAlls();

    Long countByKeyword(String keyword);

    List<DiType> findByKeyword(String keyword, Long start, Long count);

    void save(DiType type);

    void save(DiType type, InputStream is) throws IOException;

    void removeById(Long id);

    void removeByIds(Long[] ids);
}
