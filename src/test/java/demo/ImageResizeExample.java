package demo;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Programmer : pancara
 * Date       : 11/3/12
 * Time       : 12:35 PM
 */
public class ImageResizeExample {
    public static void main(String[] args) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(new File("E:/test/image/kuning.jpg"));
        Iterator iterator = ImageIO.getImageReaders(iis);

        if (!iterator.hasNext())
            return;

        ImageReader reader = (ImageReader) iterator.next();


        ImageReadParam params = reader.getDefaultReadParam();
        reader.setInput(iis, true, true);
        int w = 8;
        int h = 8;
        params.setSourceSubsampling(w, h, 0, 0);

        BufferedImage image = reader.read(0, params);

        FileOutputStream os = new FileOutputStream("E:/test/image/output.jpg");
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        encoder.encode(image);
        os.flush();
//
//        int imageRatio = unscaledHeight / unscaledWidth;
//
//        if (imageRatio >= 1) {
//            return new ImageIcon(unscaledImage.getScaledInstance(width, -1, Image.SCALE_FAST));
//        } else {
//            return new ImageIcon(unscaledImage.getScaledInstance(-1, height, Image.SCALE_FAST));
//        }
    }
}
