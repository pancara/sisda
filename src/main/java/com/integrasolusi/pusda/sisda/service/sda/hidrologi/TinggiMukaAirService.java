package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosTMA;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.TinggiMukaAir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 11:02 AM
 */
public interface TinggiMukaAirService {

    TinggiMukaAir findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    Long countAlls();

    List<TinggiMukaAir> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<TinggiMukaAir> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(TinggiMukaAir tinggiMukaAir);

    void save(TinggiMukaAir tinggiMukaAir, InputStream inputStream) throws IOException;

    List<TinggiMukaAir> findByPos(PosTMA pos);
}
