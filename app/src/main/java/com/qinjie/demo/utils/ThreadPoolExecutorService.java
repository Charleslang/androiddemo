package com.qinjie.demo.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程池，提供增加线程的服务
 *
 * @author: qinjie
 **/

public class ThreadPoolExecutorService {
    /**
     * 线程池
     */
    private static ExecutorService executor;
    /**
     * 核心线程数
     */
    private static Integer corePoolSize=5;
    /**
     * 最大线程数，普通+核心
     */
    private static Integer maximumPoolSize=15;
    /**
     * 普通空闲线程存活时间
     */
    private static long keepAliveTime=600;
    /**
     * 线程缓存大小
     */
    private static Integer queueCapacitySize=10;
    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(queueCapacitySize),namedThreadFactory);
    }
    /**
     * 功能描述: 利用线程池执行一个线程
     * @param runnable 一个执行的线程
     */
    public static void add(Runnable runnable){
        executor.execute(runnable);
    }
}
