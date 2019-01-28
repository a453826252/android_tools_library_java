package com.zlandzbt.tools.jv.utils;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    private static ExecutorService sThreadPool;

    public static ExecutorService createThreadPool(int corePoolSize,
                                                   int maximumPoolSize,
                                                   long keepAliveTime,
                                                   TimeUnit unit,
                                                   BlockingQueue<Runnable> workQueue,
                                                   ThreadFactory threadFactory) {
        sThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        return sThreadPool;
    }

    public static ExecutorService createCacheThreadPool(boolean overRide, ThreadFactory factory) {
        if (!overRide && sThreadPool != null) {
            return sThreadPool;
        }
        sThreadPool = Executors.newCachedThreadPool(factory);
        return sThreadPool;
    }

    public static ExecutorService createFixThread(int threads, ThreadFactory factory) {
        sThreadPool = Executors.newFixedThreadPool(threads, factory);
        return sThreadPool;
    }

    public static ExecutorService createScheduledThread(int coreSize, ThreadFactory factory) {
        sThreadPool = Executors.newScheduledThreadPool(coreSize, factory);
        return sThreadPool;
    }

    public static ExecutorService createSingleThread(ThreadFactory factory) {
        sThreadPool = Executors.newSingleThreadExecutor(factory);
        return sThreadPool;
    }

    public static void executor(Runnable runnable) {
        nullException();
        sThreadPool.execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        nullException();
        return sThreadPool.submit(task);
    }

    public static void releaseThreadPool(boolean immediately) {
        if (sThreadPool != null) {
            if (immediately) {
                sThreadPool.shutdownNow();
            } else {
                sThreadPool.shutdown();
            }

            sThreadPool = null;
        }
    }

    private static void nullException() {
        if (sThreadPool == null) {
            throw new IllegalArgumentException("please init ThreadPool first | 请先初始化线程池");
        }
    }

}
