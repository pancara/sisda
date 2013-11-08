package com.integrasolusi.pusda.sisda.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;

/**
 * Programmer : pancara
 * Date       : 7/20/13
 * Time       : 8:18 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence-test.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class NewsServiceImplTest {
    @Autowired
    private NewsService newsService;
    
    @Test
    public void resizeImage() throws Exception {
        FileOutputStream os = new FileOutputStream("E:/news.jpg");
        newsService.getHeadlinePictureBlob(46L, os, 60, 0);
        os.close();
        
    }
}
