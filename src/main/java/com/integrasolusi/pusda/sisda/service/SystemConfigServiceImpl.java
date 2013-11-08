package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.SystemConfigDao;
import com.integrasolusi.pusda.sisda.persistence.SystemConfig;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 2:02 AM
 */
public class SystemConfigServiceImpl implements SystemConfigService {
    private SystemConfigDao systemConfigDao;

    public void setSystemConfigDao(SystemConfigDao systemConfigDao) {
        this.systemConfigDao = systemConfigDao;
    }

    @Override
    public void incrementVisitorStats() {
        SystemConfig visitorStat = systemConfigDao.findById(SystemConfig.VISITOR_STAT_ID);
        if (visitorStat == null) {
            visitorStat = new SystemConfig();
            visitorStat.setDescription("visitor counter");
            visitorStat.setLongValue(1L);
        } else {
            Long count = visitorStat.getLongValue();
            count++;
            visitorStat.setLongValue(count);
        }

        systemConfigDao.save(visitorStat);

    }

    @Override
    public SystemConfig findById(Long id) {
        return systemConfigDao.findById(id);
    }

    @Override
    public void save(SystemConfig config) {
        systemConfigDao.save(config);
    }
}
