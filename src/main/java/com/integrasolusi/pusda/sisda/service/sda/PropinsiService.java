package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;

import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:30 AM
 */
public interface PropinsiService {
    List<Propinsi> findAlls();

    Propinsi findById(Long id);
}
