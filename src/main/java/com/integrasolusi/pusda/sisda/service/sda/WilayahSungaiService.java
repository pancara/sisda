package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:43 AM
 */
public interface WilayahSungaiService {
    
    List<WilayahSungai> findAlls();

    WilayahSungai findById(Long id);
}
