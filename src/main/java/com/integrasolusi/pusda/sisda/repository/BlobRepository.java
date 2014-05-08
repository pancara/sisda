package com.integrasolusi.pusda.sisda.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Programmer   : pancara
 * Date         : 6/22/11
 * Time         : 9:51 AM
 */
public interface BlobRepository {

    void store(BlobDataType dataType, Long id, InputStream source) throws IOException;

    void copyContent(BlobDataType dataType, Long id, OutputStream target) throws IOException;

    void remove(BlobDataType dataType, Long id);

    BufferedImage createImage(BlobDataType dataType, Long id) throws IOException;

    File getTempFile(BlobDataType dataType, Long id) throws IOException;

    Boolean isExist(BlobDataType dataType, Long id);

}
