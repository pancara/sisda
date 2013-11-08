package demo;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ScaleDescriptor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Programmer : pancara
 * Date       : 11/2/12
 * Time       : 10:17 PM
 */
public class JAIImageResizeExample {

    static
    {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }
    
    public static void main(String[] args) throws IOException {
        PlanarImage image = JAI.create("fileload", "E:/test/image/kuning.jpg");
        int destWidth = 500;
        float xScale = (float) destWidth / image.getWidth();
        float yScale = xScale;


        RenderedOp renderedOp = ScaleDescriptor.create(image, new Float(xScale), new Float(yScale),
                new Float(0.0f), new Float(0.0f), Interpolation.getInstance(Interpolation.INTERP_BICUBIC), null);
        writeJPEG(renderedOp.getAsBufferedImage(), "E:/test/image/output.jpg");
    }
    
    /*
    public static void main(String[] args) {
        try {
        
            PlanarImage image = JAI.create("fileload", args[0]);
            int destWidth = Integer.parseInt(args[1]);
            float xScale = (float) destWidth / image.getWidth();
            float yScale = 0f;
            if (args.length == 3) {
                int destHeight = Integer.parseInt(args[2]);
                yScale = (float) destHeight / (float) image.getHeight();
            } else {
                yScale = xScale;
            }

            
            RenderedOp renderedOp = ScaleDescriptor.create(image, new Float(xScale), new Float(yScale),
                    new Float(0.0f), new Float(0.0f), Interpolation.getInstance(Interpolation.INTERP_BICUBIC), null);
            writeJPEG(renderedOp.getAsBufferedImage(), "E:\\output.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public static void writeJPEG(BufferedImage input, String name) throws IOException {
        Iterator iter =
                ImageIO.getImageWritersByFormatName("JPG");
        if (iter.hasNext()) {
            ImageWriter writer = (ImageWriter) iter.next();
            ImageWriteParam iwp =
                    writer.getDefaultWriteParam();
            iwp.setCompressionMode(
                    ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(0.95f);
            File outFile = new File(name);
            FileImageOutputStream output =
                    new FileImageOutputStream(outFile);
            writer.setOutput(output);
            IIOImage image = new IIOImage(input, null, null);
            writer.write(null, image, iwp);
            output.close();
        }
    }
}