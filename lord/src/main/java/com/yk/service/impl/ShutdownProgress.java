package com.yk.service.impl;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShutdownProgress implements Runnable{

    private static final Logger logger = LogManager.getLogger();

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
        Runtime runtime = Runtime.getRuntime();
        List<String> results = Lists.newArrayList();

        try {
            Process process = runtime.exec("ps -ef | grep nmap | grep -v grep | cut -c 9-15 | xargs kill -s 9");

            logger.info("nmap进程清理完成");
        }catch (Exception e){
            logger.error(e);
        }
    }
}
