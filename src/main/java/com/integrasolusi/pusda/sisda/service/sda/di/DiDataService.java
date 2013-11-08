package com.integrasolusi.pusda.sisda.service.sda.di;

import com.integrasolusi.pusda.sisda.persistence.sda.di.DiData;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiDi;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 11:19 AM
 */
public interface DiDataService {
    DiData findById(Long id);

    Long countAlls();

    List<DiData> findAlls();

    List<DiData> findAlls(String orderProperty, OrderDir orderDir);

    List<DiData> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<DiData> findByKeyword(String keyword, Long start, Long count);

    void save(DiData data);

    void save(DiData data, InputStream inputStream) throws IOException;

    void getBlob(Long typeId, OutputStream os) throws IOException;

    void removeByIds(Long[] ids);

    List<DiData> findByDi(DiDi di);
}
