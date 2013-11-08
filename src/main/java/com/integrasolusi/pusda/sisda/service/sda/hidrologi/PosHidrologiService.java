package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosHidrologi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 7:15 PM
 */
public interface PosHidrologiService {
    PosHidrologi findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    List<PosHidrologi> findByDas(Das das);

    List<PosHidrologi> findAlls();

    Long countAlls();

    List<PosHidrologi> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<PosHidrologi> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(PosHidrologi posHidrologi);

    void save(PosHidrologi posHidrologi, InputStream inputStream) throws IOException;

    void activateById(Long id);

    PosHidrologi findActive();

    PosHidrologi findFirst();
}
