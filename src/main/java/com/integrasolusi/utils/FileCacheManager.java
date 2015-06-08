package com.integrasolusi.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * User: pancara
 * Date: 10/10/12
 * Time: 12:57 PM
 */
public class FileCacheManager {
    private String root;
    private String extension = "";

    public void setRoot(String root) {
        this.root = root;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public InputStream getStream(String path, String key) throws FileNotFoundException {
        File file = getFile(path, key);
        if (!file.exists())
            return null;
        return new FileInputStream(file);
    }


    public boolean exist(String path, String key) {
        File f = getFile(path, key);
        return f.exists();
    }

    public void store(String path, String key, InputStream is) throws IOException {
        FileOutputStream os = new FileOutputStream(getFile(path, key));
        try {
            StreamHelper.copy(is, os);
        } finally {
            StreamHelper.closeQuiet(os);
        }
    }

    public void reset(String path) throws IOException {
        FileUtils.deleteDirectory(pathToDir(path));
    }

    public File getFile(String path, String key) {
        return new File(pathToDir(path), String.format("%s.%s", key, extension));
    }

    private File pathToDir(String path) {
        File dir = new File(root, path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
