package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;
import com.integrasolusi.pusda.sisda.persistence.sda.Telaga;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 2:54 PM
 */
public interface TelagaService {

    Telaga findById(Long id);

    List<Telaga> findByDas(Das das);

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countByDas(Long das);

    Long countByDasAndKeyword(Long das, String keyword);

    List<Telaga> findAlls(Long start, Long count);

    List<Telaga> findByKeyword(String keyword, Long start, Long count);

    List<Telaga> findByDas(Long das, Long start, Long count);

    List<Telaga> findByDasAndKeyword(Long das, String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void save(Telaga telaga);
}
