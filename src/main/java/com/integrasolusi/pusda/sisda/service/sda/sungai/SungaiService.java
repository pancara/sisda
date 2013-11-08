package com.integrasolusi.pusda.sisda.service.sda.sungai;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.Sungai;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 12:37 PM
 */
public interface SungaiService {

    List<Sungai> findAlls();

    Sungai findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    Long countAlls();

    List<Sungai> findAlls(Long start, Long count);

    void save(Sungai sungai);

    void save(Sungai sungai, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

    List<Sungai> findByDas(Das das);
}
