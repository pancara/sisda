package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.Klimatologi;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosKlimatologi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:49 PM
 */
public interface KlimatologiService {

    Klimatologi findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    Long countAlls();

    List<Klimatologi> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<Klimatologi> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(Klimatologi klimatologi);

    void save(Klimatologi klimatologi, InputStream inputStream) throws IOException;


    List<Klimatologi> findByPos(PosKlimatologi pos);
}
