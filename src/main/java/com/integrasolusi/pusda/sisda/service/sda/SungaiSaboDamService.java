package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 11:57 PM
 */
public interface SungaiSaboDamService {
    SungaiSaboDam findById(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    List<SungaiSaboDam> findAlls();

    List<SungaiSaboDam> findAlls(Long start, Long count);

    List<SungaiSaboDam> findByKeyword(String keywordString, Long start, Long count);

    void save(SungaiSaboDam sungai);

    void save(SungaiSaboDam sungai, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

//    InputStream getMapStream(Long id) throws IOException;

    void getMapBlob(Long id, OutputStream outputStream) throws IOException;

    void getMapBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException;
}
