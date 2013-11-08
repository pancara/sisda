package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 8:48 AM
 */
public interface DasService {
    
    List<Das> findAlls();

    List<Das> findByWilayahSungai(WilayahSungai ws);

    Das findById(Long id);
}
