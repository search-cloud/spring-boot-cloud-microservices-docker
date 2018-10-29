package org.asion.bot.selenium.processor.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Asion.
 * @since 2018/5/17.
 */
public class MonitorThreadPoolExecutor extends ThreadPoolExecutor {

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
//        System.out.println("work_task before :" + t.getName());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
//        System.out.println("work_task after worker thread is :" + r);
    }

    @Override
    protected void terminated() {
        System.out.println(
                "terminated CorePoolSize: " + this.getCorePoolSize()
                + "；PoolSize: " + this.getPoolSize() + "；TaskCount: " + this.getTaskCount()
                + "；CompletedTaskCount: " + this.getCompletedTaskCount()
                + "；LargestPoolSize: " + this.getLargestPoolSize() + "；ActiveCount: " + this.getActiveCount()
        );
        System.out.println("ThreadPoolExecutor terminated: "+ this.toString());
    }

    public MonitorThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

}
