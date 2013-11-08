package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.SystemConfig;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 2:01 AM
 */
public interface SystemConfigService {
    void incrementVisitorStats();

    SystemConfig findById(Long id);

    void save(SystemConfig config);
}
