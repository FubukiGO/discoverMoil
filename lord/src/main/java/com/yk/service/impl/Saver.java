package com.yk.service.impl;

import com.yk.core.Engine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;

public class Saver implements Runnable {
    private static Logger logger = LogManager.getLogger();

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            for (; ; ) {
                String url = Engine.routeQueue.poll();
                if (url == null) {
                    Thread.sleep(5000L);
                } else {
                    File file = new File("./result/half-moil.db");
                    if (!file.exists()) file.createNewFile();
                    FileWriter writer = new FileWriter(file, true);

                    writer.write(url);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
