package com.integrasolusi.pusda.sisda.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;

/**
 * Programmer   : pancara
 * Date         : 6/22/11
 * Time         : 4:22 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class GalleryServiceImplTest {
    @Autowired
    private DokumentasiService dokumentasiService;


    @Test
    public void testSaveTitlePicture() throws Exception {
        java.io.InputStream is = new FileInputStream("E:/test/sisda/winter.jpg");
        dokumentasiService.saveTitlePicture(1L, is);
        is.close();
    }
}
