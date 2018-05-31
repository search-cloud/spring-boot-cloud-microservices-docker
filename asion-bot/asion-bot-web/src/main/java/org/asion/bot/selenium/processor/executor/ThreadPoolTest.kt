package org.asion.bot.selenium.processor.executor

import java.util.*
import java.util.concurrent.RejectedExecutionException

object ThreadPoolTest {
    @JvmStatic
    fun main(args: Array<String>) {
        val capacity = 10300

        val messages = ArrayList<Message>(capacity)

        run {
            var i = 0
            while (i < capacity) {
                messages.add(Message(++i, "Message-Name$i", "Message-Source$i"))
            }
        }

        for (i in 0..4) {
            ThreadPoolManager.instance.consumeThreadPool.submit(ConsumeThread<Message>())
        }

        messages.forEach { message: Message ->
            try {
                ThreadPoolManager.instance.produceThreadPool.submit(ProduceThread<Message>(message))
            } catch (e: RejectedExecutionException) {
                e.printStackTrace()
                ThreadPoolManager.sleep(3)
                ThreadPoolManager.instance.produceThreadPool.submit(ProduceThread<Message>(message))
            }
        }

        // 关闭线程池
//        ThreadPoolManager.instance.consumeThreadPool.shutdown()
//        ThreadPoolManager.instance.produceThreadPool.shutdown()
    }
}