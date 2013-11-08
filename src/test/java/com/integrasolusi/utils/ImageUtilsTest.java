package com.integrasolusi.utils;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Programmer : pancara
 * Date       : 11/15/12
 * Time       : 10:27 PM
 */
public class ImageUtilsTest {

    @Test
    public void rescale() throws IOException {
        ImageUtils imageUtils = new ImageUtils();
        FileInputStream is = new FileInputStream("E:/test/image/108.jpg");
        BufferedImage image = imageUtils.resizeImage(is, 120, 0);
    }

    @Test
    public void generateCaptcha() throws Exception {
        ImageUtils imageUtils = new ImageUtils();
        imageUtils.setPath("captcha");
        imageUtils.setBase("captcha");
        imageUtils.setType("png");
        imageUtils.setDefaultBackground(new Color(1f, 1f, 1f, 0f));
        FileOutputStream os = new FileOutputStream("E:/test/sisda/captcha.png");
        imageUtils.createCaptchaImage("Halo", os);
        os.close();
    }

    @Test
    public void generateText() throws IOException {
        ImageUtils imageUtils = new ImageUtils();
        imageUtils.setPath("captcha");
        imageUtils.setBase("captcha");
        imageUtils.setType("png");
        imageUtils.setDefaultBackground(new Color(1f, 1f, 1f, 0f));
        FileOutputStream os = new FileOutputStream("E:/test/sisda/email.png");
        imageUtils.createTextImage("koko@gmail.com", os);

        os.close();
    }
}
