package com.integrasolusi.pusda.sisda.repository;

import com.integrasolusi.utils.StreamHelper;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Programmer   : pancara
 * Date         : 6/22/11
 * Time         : 9:51 AM
 */
public class BlobRepositoryImpl implements BlobRepository {
    private String dataDir;
    private String bufferDir;
    private AtomicLong bufferIndex = new AtomicLong(1L);
    private String extension = "dat";
    private File root;
    private Logger logger = Logger.getLogger(BlobRepositoryImpl.class);
    private StreamHelper streamHelper;

    public static String getPath(String folder, Long id) {
        return String.format("%s/%d", folder, id);
    }

    private boolean isDocumentDirExist(BlobDataType dataType) {
        File file = new File(root, dataType.toString());
        return (file.exists() && file.isDirectory());
    }

    private void createDocumentDir(BlobDataType dataType) {
        File file = new File(root, dataType.toString());
        file.mkdir();
    }

    private File getDocumentFile(BlobDataType dataType, Long id) {
        return new File(root, String.format("%s/%d.%s", dataType.toString(), id, extension));
    }

    @Override
    public void store(BlobDataType dataType, Long id, InputStream source) throws IOException {
        File bufferFile = storeToBuffer(source);

        if (!isDocumentDirExist(dataType))
            createDocumentDir(dataType);

        File file = getDocumentFile(dataType, id);
        if (file.exists()) {
            file.delete();
        }
        streamHelper.copyFile(bufferFile, file);
        bufferFile.delete();
    }

    @Override
    public void copyContent(BlobDataType dataType, Long id, OutputStream target) throws IOException {
        File file = getDocumentFile(dataType, id);
        if (!file.exists()) {
            return;
        }

        File bufferFile = createBufferFile();
        streamHelper.copyFile(file, bufferFile);
        FileInputStream is = new FileInputStream(bufferFile);
        try {
            streamHelper.copy(is, target);
        } finally {
            is.close();
            bufferFile.delete();
        }
    }

    @Override
    public void remove(BlobDataType dataType, Long id) {
        File file = getDocumentFile(dataType, id);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public BufferedImage createImage(BlobDataType dataType, Long id) throws IOException {
        File bufferFile = createBufferFile();
        File file = getDocumentFile(dataType, id);
        streamHelper.copyFile(file, bufferFile);

        BufferedImage image = null;
        if (file.exists()) {
            InputStream is = new FileInputStream(bufferFile);
            try {
                image = ImageIO.read(is);
            } finally {
                is.close();
                bufferFile.delete();
            }
        }
        return image;
    }

    @Override
    public InputStream getStream(BlobDataType dataType, Long id) throws IOException {
        File source = getDocumentFile(dataType, id);
        if (!source.exists()) return null;
        File buffer = createBufferFile();
        streamHelper.copyFile(source, buffer);

        return source.exists() ? new FileInputStream(buffer) : null;
    }

    private File createBufferFile() {
        while (true) {
            Long index = bufferIndex.incrementAndGet();
            File file = new File(bufferDir, String.format("%d.tmp", index));
            if (!file.exists())
                return file;
        }
    }

    private File storeToBuffer(java.io.InputStream is) throws IOException {
        File bufferFile = createBufferFile();
        OutputStream os = new FileOutputStream(bufferFile);
        try {
            streamHelper.copy(is, os);
        } finally {
            os.close();
        }
        return bufferFile;
    }

    @Override
    public Boolean isExist(BlobDataType dataType, Long id) {
        return getDocumentFile(dataType, id).exists();
    }

    public void setStreamHelper(StreamHelper streamHelper) {
        this.streamHelper = streamHelper;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void init() {
        root = new File(dataDir);
    }

    public String getBufferDir() {
        return bufferDir;
    }

    public void setBufferDir(String bufferDir) {
        this.bufferDir = bufferDir;
    }

}
