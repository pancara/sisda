package com.integrasolusi.pusda.sisda.service.sda.bendung;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.Bendung;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 2:48 PM
 */
public interface BendungService {
    List<Bendung> findAlls();

    Bendung findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    Long countAlls();

    List<Bendung> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<Bendung> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(Bendung bendung);

    void save(Bendung bendung, InputStream inputStream) throws IOException;

    List<Bendung> findByDas(Das das);

    List<Bendung> findByWs(WilayahSungai ws);
}
