package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.persistence.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer   : pancara
 * Date         : 7/30/11
 * Time         : 12:16 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class UnitDaoImplTest {
    @Autowired
    private UnitDao unitDao;

    @Test
    public void testExistSameIndex() {
        Unit parent = unitDao.findById(2L);
        Boolean exist = unitDao.existSameIndex(parent);
        System.out.println("exist = " + exist);
    }
}
