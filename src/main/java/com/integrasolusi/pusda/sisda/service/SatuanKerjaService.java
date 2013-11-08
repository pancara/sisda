package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:21 PM
 */
public interface SatuanKerjaService {
    List<SatuanKerja> findAlls();

    SatuanKerja findById(Long id);
}
