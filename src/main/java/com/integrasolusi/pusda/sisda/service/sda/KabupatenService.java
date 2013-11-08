package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Kabupaten;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;

import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:34 AM
 */
public interface KabupatenService {
    List<Kabupaten> findByPropinsi(Propinsi propinsi);

    List<Kabupaten> findAlls();

    Kabupaten findById(Long id);
}
