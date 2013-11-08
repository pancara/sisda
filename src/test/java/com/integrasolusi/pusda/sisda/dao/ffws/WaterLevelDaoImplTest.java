package com.integrasolusi.pusda.sisda.dao.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 8/16/13
 * Time       : 8:43 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence-test.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class WaterLevelDaoImplTest {

    @Autowired
    private WaterLevelDao waterLevelDao;

    @Test
    public void testGetLatest() {
        
    }
}
