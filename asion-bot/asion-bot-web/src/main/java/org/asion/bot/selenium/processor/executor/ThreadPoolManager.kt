package org.asion.bot.selenium.processor.executor

import org.slf4j.LoggerFactory
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.SECONDS

class ThreadPoolManager private constructor() {
    // 生产者线程池
    var produceThreadPool: ThreadPoolExecutor
    // 消费者线程池
    var consumeThreadPool: ThreadPoolExecutor

    init {
        val corePoolSize = Runtime.getRuntime().availableProcessors() * 2
        produceThreadPool = MonitorThreadPoolExecutor(
                corePoolSize, corePoolSize * 2, 10, SECONDS,
                LinkedBlockingQueue())
        //        consumeThreadPool = (ThreadPoolExecutor) Executors.newScheduledThreadPool(corePoolSize);
        consumeThreadPool = MonitorThreadPoolExecutor(corePoolSize, corePoolSize, 10, SECONDS,
                LinkedBlockingQueue())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ThreadPoolManager::class.java)

        // 持有自己的单例引用
        var instance = ThreadPoolManager()

        fun sleep(seconds: Int) {
            try {
                Thread.sleep((seconds * 1000).toLong())
            } catch (e: InterruptedException) {
                // Do nothing
                e.printStackTrace()
            }
        }
    }

}