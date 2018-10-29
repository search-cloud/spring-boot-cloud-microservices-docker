package org.asion.bot.selenium.processor

import org.asion.bot.CaptureItem
import org.asion.bot.selenium.processor.executor.ConsumeThread
import org.asion.bot.selenium.processor.executor.ProduceThread
import org.asion.bot.selenium.processor.executor.ThreadPoolManager
import org.asion.bot.selenium.webdirver.ChromeDriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.*

/**
 *
 * @author Asion.
 * @since 2018/5/17.
 */
class TmallPageChromeProcessor {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val urls = ArrayList<String>(100)

            run {
                var i = 0
                while (i < 100) {
                    urls.add("")
                }
            }
            urls.forEach { url -> ThreadPoolManager.instance.produceThreadPool.submit(PageListProcessor(url)) }


            for (i in 0..4) {
                ThreadPoolManager.instance.consumeThreadPool.submit(ConsumeThread<String>())
            }

            // 关闭线程池
            ThreadPoolManager.instance.produceThreadPool.shutdown()
            ThreadPoolManager.instance.consumeThreadPool.shutdown()
        }
    }


    /**
     * 列表页处理者
     * 处理列表页中，商品详情链接
     */
    private class PageListProcessor(val url: String) : ProduceThread<CaptureItem>(url) {

        /**
         * 打开页面
         */
        private fun openPage(): List<CaptureItem> {
            val driver = ChromeDriverFactory.chromeDriver
            // 让浏览器访问
            driver.get(url)
            // 模拟点击销量

            // 模拟点击小图模式

            // 模拟点击产地中国

            // 模拟获取翻页链接，放到队列里

            // 获取 网页的 title
            println(" Page title is: " + driver.title)
            // 通过 id 找到 input 的 DOM
            val element = driver.findElement(By.id("mq"))
            // 输入关键字
            element.sendKeys("dubbo")
            // 提交 input 所在的 form
            element.submit()
            // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
            WebDriverWait(driver, 5).until {
                (it as WebDriver).title.toLowerCase().startsWith("dubbo")
            }
            // 显示搜索结果页面的 title
            println(" Page title is: " + driver.title)

            return arrayListOf()
        }

        override fun produce(): List<CaptureItem> {
            return openPage()
        }
    }

}