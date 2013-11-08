package com.integrasolusi.pusda.sisda.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Programmer   : pancara
 * Date         : 6/22/11
 * Time         : 6:52 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class BlobRepositoryImplTest {
    @Autowired
    private BlobRepository blobRepository;

    @Test
    public void testStoreDocument() throws Exception {
        FileInputStream is = new FileInputStream("E:/test/sisda/winter.jpg");
        blobRepository.store(BlobDataType.DOKUMENTASI_HEADLINE, 1L, is);
        is.close();
    }

    @Test
    public void testReadDocument() throws IOException {
        FileOutputStream os = new FileOutputStream("E:/test/sisda/read.jpg");
        blobRepository.copyContent(BlobDataType.DOKUMENTASI_HEADLINE, 1L, os);
        os.close();
    }
    @Test
    public void testRemoveDocument() {
        blobRepository.remove(BlobDataType.DOKUMENTASI_HEADLINE, 1L);
    }
}
