package com.integrasolusi.demo;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: pancara
 * Date: 11/17/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImageIoDemo {
    @Test
    public void readImage() throws IOException {
        FileInputStream is = new FileInputStream("E:/test/sisda/Jellyfish.jpg");
        BufferedImage image = ImageIO.read(is);
        is.close();

        OutputStream os = new FileOutputStream("E:/test/sisda/output.jpg");
        ImageIO.write(image, "jpeg", os);
        os.close();
    }
}
