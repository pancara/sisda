package com.integrasolusi.utils;

import java.io.*;

/**
 * Programmer   : pancara
 * Date         : 6/22/11
 * Time         : 3:59 PM
 */
public class StreamHelper {
    private static int bufferSize = 1024 * 16;


    public static void copy(InputStream source, OutputStream target, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        while (source.available() > 0) {
            int readCount = source.read(buffer);
            target.write(buffer, 0, readCount);
        }
    }

    public static void copy(InputStream source, OutputStream target) throws IOException {
        copy(source, target, bufferSize);
    }

    public static void copyFile(File source, File target) throws IOException {
        InputStream is = new FileInputStream(source);
        OutputStream os = new FileOutputStream(target);
        try {
            copy(is, os);
        } finally {
            StreamHelper.closeQuiet(is);
            StreamHelper.closeQuiet(os);
        }
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public static void closeQuiet(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Exception e) {
        }
    }
}