package com.integrasolusi.pusda.sisda.tool.ffws;

import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 9:30 PM
 */
public class PopulatorManager {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PopulatorManager.class);

    private DataPopulator dataPopulator;
    
    private ScheduledExecutorService scheduler;
    private int delay = 60 * 5 / 2; // delay in seconds
    private int awaitTermination = 30;// seconds
    private boolean autoStart = true;
    private boolean running;

    public void setDataPopulator(DataPopulator dataPopulator) {
        this.dataPopulator = dataPopulator;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setAwaitTermination(int awaitTermination) {
        this.awaitTermination = awaitTermination;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public synchronized void startAuto() {
        if (autoStart) start();
    }

    public synchronized void start() {
        logger.info("starting data populator");
        ensureShutdown();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(dataPopulator, 0, delay, TimeUnit.SECONDS);
    }

    private void ensureShutdown() {
        if (scheduler != null && !scheduler.isTerminated()) {
            boolean terminated = false;
            try {
                terminated = scheduler.awaitTermination(awaitTermination, TimeUnit.SECONDS);
            } catch (Exception e) {
            }
            if (!terminated) {
                scheduler.shutdownNow();
            }
            dataPopulator.reset();
        }
    }

    public synchronized void stop() {
        logger.info("stopping data populator");

        dataPopulator.stop();
        scheduler.shutdown();
        ensureShutdown();
    }

    public boolean isRunning() {
        return scheduler != null && !scheduler.isTerminated() && !scheduler.isShutdown();
    }
}
