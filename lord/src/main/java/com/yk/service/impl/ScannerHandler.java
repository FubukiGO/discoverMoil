package com.yk.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: akhan
 * @Description:
 * @Date: 下午6:51 2018/8/20
 */
public class ScannerHandler {

    public void __START__() {
        ExecutorService send = Executors.newCachedThreadPool();
    }

    class addressManager implements Runnable {
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

        }
    }
}
