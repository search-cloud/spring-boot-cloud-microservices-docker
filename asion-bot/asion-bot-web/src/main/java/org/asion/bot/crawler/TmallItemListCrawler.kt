package org.asion.bot.crawler

import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItemManger
import org.asion.bot.helper.Counter
import org.asion.bot.helper.RedisClientLocal
import org.asion.bot.parser.Parser
import java.util.regex.Pattern

/**
 * Tmall商品爬虫
 *
 * 暂时只爬取护肤、彩妆销量前240的商品。
 *
 *
 * @author Asion
 * @since 2018-05-03
 */
class TmallItemListCrawler(private var captureItemManger: CaptureItemManger, private val parser: Parser<CaptureItem>) : WebCrawler() {

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    override fun shouldVisit(referringPage: Page?, url: WebURL): Boolean {
        val href = url.url
        return href.startsWith("https://list.tmall.com/search_product.htm") &&
        LEAF_CATEGORIES1.matcher(href).matches()
    }

    override fun handleUrlBeforeProcess(curURL: WebURL): WebURL {
        return curURL
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    override fun visit(page: Page?) {
        val url = page!!.webURL.url
        println("URL: $url")

        if (page.parseData is HtmlParseData) {
            val htmlParseData = page.parseData as HtmlParseData
            val text = htmlParseData.text
            val html = htmlParseData.html
            val links = htmlParseData.outgoingUrls
//            csv.saveOutgoingLinks(links)

            println("Text length: " + text.length)
            println("Html length: " + html.length)
            println("Html: " + html.toString())
            println("Number of outgoing links: " + links.size)

            // 解析html获取相应的数据
            val items = parser.parse(html)
            // 保存数据到数据库
//            seekManger.save(items)
            // 保存到redis
            if (items.isNotEmpty()) {
                RedisClientLocal.set(url, Counter.safeAdd().get())
                items.forEach {
                    it.index = Counter.safeAdd().get()
                    RedisClientLocal.set(it.originId.toString(), it, 3)
                }
            }

        }
    }

    companion object {
        //    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");

        /**
         * 1. 销量倒序排序：sort=d
         * 2. 小图浏览模式：style=l
         * 3. 页码简单处理，每页28*3=84条：3*84=252条
         * 4. 类目
         */
        private val LEAF_CATEGORIES1 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*&(prop=21299:27772).*&(sort=d).*&(style=l).*""")

    }

}