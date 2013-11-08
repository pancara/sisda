package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.KualitasAir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 6:45 PM
 */
public interface KualitasAirService {
    KualitasAir findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    List<KualitasAir> findByDas(Das das);

    Long countAlls();

    List<KualitasAir> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<KualitasAir> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(KualitasAir kualitasAir);

    void save(KualitasAir kualitasAir, InputStream inputStream) throws IOException;
}
