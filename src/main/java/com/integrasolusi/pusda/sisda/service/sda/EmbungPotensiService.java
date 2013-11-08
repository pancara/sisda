package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;
import com.integrasolusi.pusda.sisda.persistence.sda.EmbungPotensi;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 3:09 PM
 */
public interface EmbungPotensiService {
    List<EmbungPotensi> findByPropinsi(Propinsi propinsi);

    EmbungPotensi findById(Long id);

    List<EmbungPotensi> findByDas(Das das);
}
