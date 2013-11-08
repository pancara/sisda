package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.SatuanKerjaDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:21 PM
 */
public class SatuanKerjaServiceImpl implements SatuanKerjaService {
    private SatuanKerjaDao satuanKerjaDao;

    public void setSatuanKerjaDao(SatuanKerjaDao satuanKerjaDao) {
        this.satuanKerjaDao = satuanKerjaDao;
    }

    @Override
    public List<SatuanKerja> findAlls() {
        return satuanKerjaDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public SatuanKerja findById(Long id) {
        return satuanKerjaDao.findById(id);
    }
}
