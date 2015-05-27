package com.integrasolusi.demo;

import java.io.*;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 5:02 PM
 */
public class ImageResizeDemo {

    public static void resizeMedium(File originalFile, File resizedFile, int newWidth) throws IOException {
        InputStream inputStream = new FileInputStream(originalFile);
        OutputStream outputStream = new FileOutputStream(resizedFile);

//        SeekableStream s = SeekableStream.wrapInputStream(inputStream, true);
//        RenderedOp image = JAI.create("stream", s);
//        ((OpImage) image.getRendering()).setTileCache(null);
//
//        double scale = (double)newWidth / image.getWidth();
//        System.out.println(scale);
//
//        ParameterBlock pb = new ParameterBlock();
//        pb.addSource(image); // The source image
//        pb.add(scale); // The xScale
//        pb.add(scale); // The yScale
//        pb.add(0.0); // The x translation
//        pb.add(0.0); // The y translation

//        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING,
//                RenderingHints.VALUE_RENDER_QUALITY);
//          RenderedOp resizedImage = JAI.create("SubsampleAverage", 
//              image, scale, scale, qualityHints);
//        RenderedOp resizedImage = JAI.create("SubsampleAverage", pb, qualityHints);

        // lastly, write the newly-resized image to an output stream, in a specific encoding
//        JAI.create("encode", resizedImage, outputStream, "PNG", null);
    }

 
}
