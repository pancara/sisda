package com.integrasolusi.utils;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.Random;

/**
 * Programmer   : pancara
 * Date         : 6/20/11
 * Time         : 10:15 AM
 */
public class ImageUtils {
    private String path;
    private String base;
    private String type;

    private Color defaultBackground;


    private BufferedImage createCaptchaImage(String captchaText, File background) throws IOException {
        FileInputStream fis = new FileInputStream(background);
        BufferedImage image = ImageIO.read(fis);
        fis.close();

        Graphics2D g = image.createGraphics();
        g.setTransform(new AffineTransform());
        g.setColor(Color.white);

        Font font = new Font("Verdana", Font.BOLD, 26);
        g.setFont(font);

        FontRenderContext frc = g.getFontRenderContext();
        Rectangle2D textBounds = g.getFont().getStringBounds(captchaText, frc);

        int x = (int) (image.getWidth() - textBounds.getWidth()) / 2;
        int y = (int) textBounds.getHeight();
        g.drawString(captchaText, x, y);
        return image;
    }

    public void createCaptchaImage(String captchaText, OutputStream outputStream) throws Exception {
        File file = getImageFile();
        BufferedImage image = createCaptchaImage(captchaText, file);
        ImageIO.write(image, type, outputStream);
        outputStream.flush();

    }

    private File getImageFile() throws IOException {
        Random random = new Random(System.currentTimeMillis());
        try {
            int index = random.nextInt(3);
            String filename = String.format("%s/%s-%d.%s", path, base, index, type);
            ClassPathResource resource = new ClassPathResource(filename);
            return resource.getFile();
        } catch (IOException e) {
            String filename = String.format("%s/%s.%s", path, base, type);
            ClassPathResource resource = new ClassPathResource(filename);
            return resource.getFile();

        }
    }

    public void writeImage(BufferedImage image, String type, OutputStream os) throws Exception {
        ImageIO.write(image, type, os);
        os.flush();
    }

    public BufferedImage resizeImage(InputStream imageStream, int scaledWidth, int scaledHeight) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(imageStream);
        Iterator iterator = ImageIO.getImageReaders(iis);

        if (!iterator.hasNext()) {
            return null;
        }

        ImageReader reader = (ImageReader) iterator.next();
        ImageReadParam params = reader.getDefaultReadParam();
        reader.setInput(iis, true, true);
        int imageIndex = 0;

        int w = scaledWidth == 0 ?
                scaledHeight != 0 ?
                        reader.getHeight(imageIndex) / scaledHeight :
                        1 :
                reader.getWidth(imageIndex) / scaledWidth;

        int h = scaledHeight == 0 ?
                scaledWidth != 0 ?
                        reader.getWidth(imageIndex) / scaledWidth :
                        1 :
                reader.getHeight(imageIndex) / scaledHeight;


        params.setSourceSubsampling(w, h, 0, 0);
        return reader.read(imageIndex, params);
    }

//    private static RenderedOp scale(RenderedOp image, float scale) {
//        ParameterBlock scaleParams = new ParameterBlock();
//        scaleParams.addSource(image);
//        scaleParams.add(scale).add(scale).add(0.0f).add(0.0f);
//        scaleParams.add(Interpolation.getInstance(Interpolation.INTERP_BICUBIC_2));
//
//        // Quality related hints when scaling the image
//        RenderingHints scalingHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        scalingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        scalingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        scalingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        scalingHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//        scalingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//        scalingHints.put(JAI.KEY_BORDER_EXTENDER, BorderExtender.createInstance(BorderExtender.BORDER_COPY));
//
//        return JAI.create("scale", scaleParams, scalingHints);
//    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void createTextImage(String email, OutputStream outputStream) {
        try {
            BufferedImage image = drawTextImage(email);
            ImageIO.write(image, type, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage drawTextImage(String email) {
        BufferedImage image = new BufferedImage(300, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(defaultBackground);
        g.fillRect(0, 0, 300, 20);
        g.setTransform(new AffineTransform());
        g.setColor(Color.decode("0xAB0D22"));
        Font font = new Font("Trebuchet MS", Font.BOLD, 10);
        g.setFont(font);

        FontRenderContext frc = g.getFontRenderContext();
        Rectangle2D textBounds = g.getFont().getStringBounds(email, frc);

        int x = 0;
        int y = (int) textBounds.getHeight();
        g.drawString(email, x, y);
        return image;
    }

    public Color getDefaultBackground() {
        return defaultBackground;
    }

    public void setDefaultBackground(Color defaultBackground) {
        this.defaultBackground = defaultBackground;
    }
}
