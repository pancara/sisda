package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer : pancara
 * Date       : 12/20/12
 * Time       : 1:22 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class RegulationFolderServiceImplTest {
    @Autowired
    private RegulationFolderService regulationFolderService;
    
    @Test
    public void testMoveUp() {
        Folder folder = regulationFolderService.findById(55L);
        regulationFolderService.moveUp(folder);
    }

    @Test
    public void testMoveDown() {
        Folder folder = regulationFolderService.findById(55L);
        regulationFolderService.moveDown(folder);
    }

    @Test
    public void normalize() {
        regulationFolderService.reindex();
    }
}
