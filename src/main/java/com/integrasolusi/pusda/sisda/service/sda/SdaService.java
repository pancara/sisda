package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.Sda;

import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 1:56 PM
 */
public interface SdaService {
    List<Sda> findActive();

    Sda findById(Long id);
}
