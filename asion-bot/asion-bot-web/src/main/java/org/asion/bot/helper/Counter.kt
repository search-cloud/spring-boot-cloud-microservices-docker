package org.asion.bot.helper

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

object Counter {

    private val ai = AtomicInteger()
    private var index = 0

    // 使用CAS实现线程安全的计数器
    fun safeAdd(): AtomicInteger {
        //用一个for循环,如果没有计数成功的话,会一直执行这段代码，知道计数成功break为止
        while (true) {
            val j = ai.get()    //读取value值,赋给j,  j在线程的工作内存中
            //将主内存中的值(current)与工作内存中的值j相比较,如果相等的话，说明工作内存中的j值仍然是value的最新值
            //计数运算对当前i操作没有问题,将value值设为j+1,因为value是volatile的，所以写的时候也就写到了主内存
            val b = ai.compareAndSet(j, j + 1)
            if (b) {    //如果+1成功
                break
            }
        }
        return ai
    }

    // 非安全的线程计数器
    fun add() {
        index += 1
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val es = Executors.newCachedThreadPool()
        for (i in 0..999) {
            es.execute {
                add()
                safeAdd()
            }
        }
        println("index=${index}")
        println("ai=" + ai.get())
    }

}
