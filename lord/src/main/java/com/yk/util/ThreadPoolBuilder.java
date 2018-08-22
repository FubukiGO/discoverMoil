package com.yk.util;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: akhan
 * @Description:
 * @Date: 上午11:37 2018/8/21
 */
public class ThreadPoolBuilder {
    public static ExecutorService build(String threadname, int minimumPoolSize, int maximumPoolSize) {
        ThreadFactory threadNameFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat(threadname + "-%d").build();

        return new ThreadPoolExecutor(
                minimumPoolSize,
                maximumPoolSize,
                0L,
                TimeUnit.MILLISECONDS,
                Queues.newLinkedBlockingQueue(1024),
                threadNameFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
