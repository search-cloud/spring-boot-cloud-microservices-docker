package org.asion.bot.selenium.processor

import org.asion.bot.CaptureItem
import org.asion.bot.helper.RedisClientLocal
import org.asion.bot.parser.ItemListParser
import org.asion.bot.parser.Parser
import org.asion.bot.selenium.HostAndPort
import org.asion.bot.selenium.proxy.ProxyBuilder
import org.asion.bot.selenium.webdirver.PhantomJsExecutor
import java.util.concurrent.Executors

/**
 *
 * @author Asion.
 * @since 2018/5/17.
 */
open class TmallPagePhantomJsExecutorProcessor {

    /**
     * 列表页处理者
     * 处理列表页中，商品详情链接
     */
    open class PageListProcessor(val url: String, private val parser: Parser<CaptureItem>) : Runnable {

        /**
         * 处理页面
         */
        private fun process() {
            println("one list process start!")

            // 代理的ip，null 表示不设置代理
            val hostAndPort: HostAndPort? = ProxyBuilder.getHostAndPort()
            println("proxy: $hostAndPort")
            println("url: $url")

            val html = PhantomJsExecutor.execute(url, hostAndPort)
            if (html != null && html != "") {
                println("execute parse start!")
                val items = parser.parse(html)
                println("execute parse finish!, item size: ${items.size}")
                // 保存到redis
                items.forEach { item ->
                    if (item.originId != null) {
                        RedisClientLocal.set(item.originId.toString(), item, dbIndex)
                    }
                }
            } else {
                println("execute PhantomJs fail")
            }

            println("one list process finish!")
        }

        override fun run() {
            process()
        }

    }

    companion object {
        const val dbIndex = 4

        @JvmStatic
        fun main(args: Array<String>) {
            val urls = RedisClientLocal.keys("https://list.tmall.com/*", 0)
            //
            val itemListParser = ItemListParser()
            val corePoolSize = Runtime.getRuntime().availableProcessors() * 2
            val pool = Executors.newScheduledThreadPool(corePoolSize)
            urls.forEach { url -> pool.submit(PageListProcessor(url, itemListParser)) }

        }
    }

}