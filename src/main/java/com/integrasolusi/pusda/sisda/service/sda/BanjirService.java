package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.Banjir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 10:52 PM
 */
public interface BanjirService {
    List<Banjir> findByDas(Das das);
    
    Banjir findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;


    Long countAlls();

    List<Banjir> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<Banjir> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(Banjir banjir);

    void save(Banjir banjir, InputStream inputStream) throws IOException;
}
