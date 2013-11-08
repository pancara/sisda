package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer   : pancara
 * Date         : 7/23/11
 * Time         : 6:38 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/aspect.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class UnitServiceImplTest {
    @Autowired
    private UnitService unitService;

    @Test
    public void testFindByParent() throws Exception {
      
    }

    @Test
    public void normalizeChildrenIndex() {
        Unit parent = unitService.findById(2L);
        unitService.normalizeChildrenIndex(parent);
    }

    @Test
    public void moveDown() {
        unitService.moveDown(1002L);
    }

    @Test
    public void moveUp() {
        unitService.moveUp(1002L);
    }

    @Test
    public void removeById() {
        unitService.removeById(8L);
    }
}
