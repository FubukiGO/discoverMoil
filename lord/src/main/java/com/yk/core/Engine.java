package com.yk.core;

import com.yk.util.ThreadPoolBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: akhan
 * @Description:
 * @Date: 上午10:30 2018/8/20
 */

public class Engine {
    private static final Logger logger = LogManager.getLogger();

    public static Queue<String> routeQueue = new LinkedBlockingQueue<>();

    public static ExecutorService pool1 = ThreadPoolBuilder.build("raider", 10, 200);

    static ExecutorService pool2 = Executors.newSingleThreadExecutor();

    static String[] localArgs = null;

    public static Screen localScreen = null;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            logger.warn("args can not was null");
            System.exit(0);
        }else localArgs = args;

        logger.info("===============start!================");
        localScreen = new Screen();
        localScreen.pack();
        localScreen.setVisible(true);

        System.exit(0);
//        if (args.length ==0 ) {
//            logger.warn("args can not was null");
//            System.exit(0);
//        }
//        pool2.execute(new Saver());
//        ScannerHandler scannerHandler = new ScannerHandler(args);
//        scannerHandler.__START__();
    }
}
