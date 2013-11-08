package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;
import com.integrasolusi.pusda.sisda.persistence.sda.Embung;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 2:54 PM
 */
public interface EmbungService {
    List<Embung> findByPropinsi(Propinsi propinsi);

    Embung findById(Long id);

    List<Embung> findByDas(Das das);

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countByDas(Long das);

    Long countByDasAndKeyword(Long das, String keyword);

    List<Embung> findAlls(Long start, Long count);

    List<Embung> findByKeyword(String keyword, Long start, Long count);

    List<Embung> findByDas(Long das, Long start, Long count);

    List<Embung> findByDasAndKeyword(Long das, String keyword, Long start, Long count);

    void save(Embung embung);

    void removeByIds(Long[] ids);
}
