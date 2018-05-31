package org.asion.bot.selenium.processor

import com.ytx.seeker.selenium.proxy.ProxyBuilder
import org.asion.bot.CaptureItem
import org.asion.bot.helper.RedisClientLocal
import org.asion.bot.parser.WebDriverDetailParser
import org.asion.bot.parser.WebDriverListParser
import org.asion.bot.selenium.HostAndPort
import org.asion.bot.selenium.processor.executor.ConsumeThread
import org.asion.bot.selenium.processor.executor.ProduceThread
import org.asion.bot.selenium.processor.executor.ThreadPoolManager
import org.asion.bot.selenium.webdirver.PhantomJsDriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

/**
 *
 * @author Asion.
 * @since 2018/5/17.
 */
open class TmallPagePhantomJsProcessor {

    /**
     * 列表页处理者
     * 处理列表页中，商品详情链接
     */
    open class PageListProcessor(val url: String, private val parser: WebDriverListParser) : ProduceThread<CaptureItem>(url) {

        /**
         * 打开页面
         */
        private fun openPage(): List<CaptureItem> {
            println("one list process start!")
            // 代理的ip，null 表示不设置代理
            val hostAndPort: HostAndPort? = ProxyBuilder.getHostAndPort()
            println("proxy: $hostAndPort")
            println("url: $url")

            val driver = PhantomJsDriverFactory.getDriver(hostAndPort)
            // 让浏览器访问
            driver.get(url)

            // 模拟点击销量
            // 模拟点击小图模式
            // 模拟点击产地中国
            // 模拟获取翻页链接，放到队列里
            var items: List<CaptureItem> = ArrayList()

            try {// 等待页面加载完毕，间隔10秒
                WebDriverWait(driver, 10).until {
                    ExpectedConditions.visibilityOfElementLocated(By.id("J_ItemList"))
                }
                items = parser.parse(driver)
            } catch (e: Exception) {
                e.printStackTrace()
                // 忽略异常继续
            }

            // 解析完driver退出释放资源
            driver.quit()

            println("one list process finish!")
            return items
        }

        override fun produce(): List<CaptureItem> {
            return openPage()
        }

    }

    /**
     * 列表页处理者
     * 处理列表页中，商品详情链接
     */
    class PageDetailProcessor(private val parser: WebDriverDetailParser) : ConsumeThread<CaptureItem>() {

        /**
         * 再次处理列表抓取回来的item，保存
         *
         * @param item 商品
         */
        private fun process(item: CaptureItem) {
            println("one detail process start!")
            val hostAndPort: HostAndPort? = ProxyBuilder.getHostAndPort()
            println("proxy: $hostAndPort")
            println("itemOriginId: ${item.originId}, url: ${item.detailLink}")
            val driver = PhantomJsDriverFactory.getDriver(hostAndPort)
            // 让浏览器访问
            driver.get(item.detailLink)

            // 等待页面加载完毕，间隔10秒
            WebDriverWait(driver, 10).until {
                ExpectedConditions.visibilityOfElementLocated(By.id("J_DetailMeta"))
            }
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
            val parseItem = parser.parse(item, driver)

            // 另一种解析方式
//            val html = PhantomJsExecutor.execute(item.detailLink, hostAndPort)
//            val parseItem = parser.parse(html, item)

            parseItem.grab = 1
            // 保存到redis
            RedisClientLocal.set(parseItem.originId.toString(), parseItem, dbIndex)
//

            println("one detail process finish!")
        }

        override fun consume(item: CaptureItem) {
            process(item)
        }

    }

    /**
     * 保存线程
     */
    class PageDetailSaveProcessor : ConsumeThread<CaptureItem>() {

        /**
         * 再次处理列表抓取回来的item，保存
         *
         * @param item 商品
         */
        private fun process(item: CaptureItem) {
            println("one detail process start!")
            println("itemOriginId: ${item.originId}, url: ${item.detailLink}")

            // 保存到redis
            if (item.originId != null) {
                RedisClientLocal.set(item.originId.toString(), item, dbIndex)
            }

            println("one detail process finish!")
        }

        override fun consume(item: CaptureItem) {
            process(item)
        }
    }

    companion object {
        const val dbIndex = 4

        @JvmStatic
        fun main(args: Array<String>) {
            val urls = RedisClientLocal.keys("https://list.tmall.com/*", 0)
            //
            val itemListParser = WebDriverListParser()
            urls.forEach { url -> ThreadPoolManager.instance.produceThreadPool.submit(PageListProcessor(url, itemListParser)) }

            // 5个消费线程
            for (i in 0..4) {
                ThreadPoolManager.instance.consumeThreadPool.submit(PageDetailSaveProcessor())
            }

            // 关闭线程池
//            ThreadPoolManager.instance.produceThreadPool.shutdown()
//            ThreadPoolManager.instance.consumeThreadPool.shutdown()
        }
    }

}