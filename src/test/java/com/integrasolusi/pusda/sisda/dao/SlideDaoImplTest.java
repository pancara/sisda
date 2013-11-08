package com.integrasolusi.pusda.sisda.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 7:06 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class SlideDaoImplTest {

    @Autowired
    private SlideDao slideDao;

    @Test
    public void testGetMaxIndex() throws Exception {
        Long maxIndex = slideDao.getMaxIndex();
        System.out.println("maxIndex = " + maxIndex);

    }
}
