package org.asion.bot.selenium.controller

import org.asion.bot.parser.WebDriverDetailParser
import org.asion.bot.parser.WebDriverListParser
import org.asion.bot.selenium.processor.TmallPagePhantomJsProcessor
import org.asion.bot.selenium.processor.TmallPageProcessor
import org.asion.bot.selenium.processor.executor.ThreadPoolManager
import org.asion.bot.CaptureItemManger
import org.asion.bot.helper.RedisClientLocal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.codecraft.webmagic.Spider.create
import us.codecraft.webmagic.downloader.PhantomJSDownloader


/**
 *
 * @author Asion.
 * @since 2018/5/7.
 */
@RestController
@RequestMapping("/seek")
class TmallWebController {

    @Autowired
    lateinit var captureItemManger: CaptureItemManger
    @Autowired
    lateinit var parser: WebDriverListParser
    @Autowired
    lateinit var detailParser: WebDriverDetailParser

    @RequestMapping("/tmall")
    fun tmallSearch(keyWord: String): Map<String, Any> {
        val spider = create(TmallPageProcessor(captureItemManger, keyWord))
        val phantomJSDownloader = PhantomJSDownloader("/Users/Asion/Workstation/Tools/phantomjs-2.1.1-macosx/bin/phantomjs",
                "/Users/Asion/Workstation/Personal/java-workspace/ytx-seeker/ytx-seeker-web/src/main/resources/templates/crawl.js")
        //免费代理不稳定老挂
//        phantomJSDownloader.proxyProvider = SimpleProxyProvider.from(Proxy("0.0.0.0", 0000))
        spider.setDownloader(phantomJSDownloader)
        spider.addUrl("https://www.tmall.com/?spm=a2e1o.8267851.20161122.1.6c517b7d4bcbpv").thread(5).run()
        return mapOf()
    }

    @RequestMapping("/tmall/p")
    fun tmallSeek(): Map<String, Any> {

        // 5个消费线程
        for (i in 0..4) {
            ThreadPoolManager.instance.consumeThreadPool.submit(TmallPagePhantomJsProcessor.PageDetailProcessor(detailParser))
        }

        val urls = RedisClientLocal.keys("https://list.tmall.com/*", 0)
        // 生产线程
        urls.forEach { url -> ThreadPoolManager.instance.produceThreadPool.submit(TmallPagePhantomJsProcessor.PageListProcessor(url, parser)) }

        return mapOf()
    }
}