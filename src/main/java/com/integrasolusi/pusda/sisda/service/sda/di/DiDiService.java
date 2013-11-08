package com.integrasolusi.pusda.sisda.service.sda.di;

import com.integrasolusi.pusda.sisda.persistence.sda.di.DiDi;
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
public interface DiDiService {
    
    List<DiDi> findAlls();

    List<DiDi> findAlls(Long start, Long count);

    DiDi findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    Long countAlls();

    void save(DiDi di);

    void save(DiDi di, InputStream is) throws IOException;

    void removeById(Long id);

    void removeByIds(Long[] ids);

    List<DiDi> findByDiType(DiType type);
}
