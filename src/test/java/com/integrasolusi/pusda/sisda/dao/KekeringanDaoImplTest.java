package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.dao.sda.KekeringanDao;
import com.integrasolusi.pusda.sisda.dao.sda.WilayahSungaiDao;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer : pancara
 * Date       : 10/13/12
 * Time       : 2:46 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class KekeringanDaoImplTest {

    @Autowired
    private KekeringanDao kekeringanDao;
    
    @Autowired
    private WilayahSungaiDao wilayahSungaiDao;

    @Test
    public void testGetYearByWilayahSungai() throws Exception {
        WilayahSungai ws = wilayahSungaiDao.findById(1L);
        kekeringanDao.getYearByWilayahSungai(ws);

    }
}
