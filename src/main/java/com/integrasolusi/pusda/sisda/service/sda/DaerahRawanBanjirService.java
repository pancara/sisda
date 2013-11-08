package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.DaerahRawanBanjir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 3:25 PM
 */
public interface DaerahRawanBanjirService {
    DaerahRawanBanjir findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    List<DaerahRawanBanjir> findByDas(Das das);

    Long countAlls();

    List<DaerahRawanBanjir> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<DaerahRawanBanjir> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(DaerahRawanBanjir daerahRawanBanjir);

    void save(DaerahRawanBanjir drb, InputStream inputStream) throws IOException;
}
