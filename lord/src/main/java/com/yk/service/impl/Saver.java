package com.yk.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.yk.core.Engine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

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
            List<String> list = Lists.newArrayList();
            for (; ; ) {
                String url = Engine.routeQueue.poll();
                if (url == null && list.size() > 0) {
                    File file = new File("./half-moil.db");
                    if (!file.exists()) file.createNewFile();
                    FileWriter writer = new FileWriter(file, true);

                    writer.write(Joiner.on("\r\n").join(list) + "\r\n");
                    writer.flush();
                    writer.close();
                    writer = null;
                    logger.info("文件已保存,新增{}条", list.size());
                    list.clear();
                } else if (url != null) {
                    list.add(url);
                } else {
                    Thread.sleep(5000L);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
