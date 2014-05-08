package com.integrasolusi.pusda.sisda.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        FileInputStream is = new FileInputStream("E:/test/sisda/Jellyfish.jpg");
        blobRepository.store(BlobDataType.DOKUMENTASI_HEADLINE, 1L, is);
        is.close();
    }

    @Test
    public void createImage() throws IOException {
        FileInputStream is = new FileInputStream("E:/test/sisda/Jellyfish.jpg");
        BufferedImage image = ImageIO.read(is);
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
