package com.integrasolusi.pusda.sisda.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 1:29 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence-test.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class VisitorCounterServiceImplTest {
    @Autowired
    private VisitorCounterService visitorCounterService;

    @Test
    public void testIncrementCount() throws Exception {
        visitorCounterService.incrementCount(new Date());
        
    }
}
