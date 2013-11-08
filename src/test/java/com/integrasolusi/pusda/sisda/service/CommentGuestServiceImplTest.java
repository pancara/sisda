package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: pancara
 * Date: 9/4/12
 * Time: 4:42 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class CommentGuestServiceImplTest {
    @Autowired
    private CommentGuestService commentGuestService;

    @Test
    public void testCountAlls() throws Exception {
        Long count = commentGuestService.countAlls();
        System.out.println(count);
        
        CommentGuest comment = commentGuestService.findById(45L);
    }
    
    @Test
    public void findResponse() {
        CommentGuest comment = commentGuestService.findById(45L);
        commentGuestService.findResponse(comment);
        
    }
}
