package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.AirTanah;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 3:18 PM
 */
public interface AirTanahService {

    List<AirTanah> findByWilayahSungai(WilayahSungai ws);

    AirTanah findById(Long id);

    void getBlob(Long id, OutputStream outputStream) throws IOException;

    Long countAlls();

    Long countByKeyword(String keyword);

    List<AirTanah> findAlls(Long start, Long count);

    List<AirTanah> findByKeyword(String keyword, Long start, Long count);

    void save(AirTanah airTanah, InputStream is) throws IOException;

    void save(AirTanah airTanah);

    void removeByIds(Long[] ids);
}
