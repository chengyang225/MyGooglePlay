package com.testdemo.chanian.mygoogleplay.manage;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChanIan on 16/5/26.
 */
//线程池代理
public class ThreadPoolProxy {
    private ThreadPoolExecutor mExecutor;
    private int corePoolSize;
    private int maxPoolSize;
    private long keepAliveTime;

    public ThreadPoolProxy( int corePoolSize, int maxPoolSize,long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        init();
    }

    //初始化线程池
    private void init() {
        if(mExecutor==null||mExecutor.isShutdown()||mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class){//防止并发

                if(mExecutor==null||mExecutor.isShutdown()||mExecutor.isTerminated()) {
                    //阻塞队列
                    LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
                    //线程工厂
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    //异常捕获
                    ThreadPoolExecutor.DiscardPolicy handler = new ThreadPoolExecutor.DiscardPolicy();
                    //激活周期单位
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    mExecutor=  new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
                            unit, blockingDeque, threadFactory,
                            handler);
                }
            }
        }
    }

    //提交任务
    public void submit(Runnable task){
        mExecutor.submit(task);
    }
    //移除任务
    public void remove(Runnable task){
        mExecutor.remove(task);
    }
    //执行任务
    public void execute(Runnable task){
        mExecutor.execute(task);
    }

}
