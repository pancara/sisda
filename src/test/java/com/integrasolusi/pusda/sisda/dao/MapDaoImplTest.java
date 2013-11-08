package com.integrasolusi.pusda.sisda.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: pancara
 * Date: 9/29/12
 * Time: 7:35 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class MapDaoImplTest {
    @Autowired
    private MapDao mapDao;
    
    @Test
    public void testMapDao() {
        
    }
}
