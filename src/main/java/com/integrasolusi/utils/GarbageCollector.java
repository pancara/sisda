package com.integrasolusi.utils;

import org.apache.log4j.Logger;

/**
 * Programmer   : pancara
 * Date         : 7/27/11
 * Time         : 8:34 PM
 */
public class GarbageCollector implements Runnable {
    private static Logger logger = Logger.getLogger(GarbageCollector.class);
    private Thread thread;
    private boolean stopped;

    public void start() {
        thread = new Thread(this);
        stopped = false;
        thread.start();
    }

    public void stop() {
        stopped = true;
    }

    @Override
    public void run() {
        while (!stopped) {
            System.gc();
            try {
                Thread.currentThread().sleep(7200);
            } catch (InterruptedException ie) {
            }
        }
    }
}
