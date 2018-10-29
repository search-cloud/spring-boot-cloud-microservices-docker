package org.asion.bot.crawler

import com.alibaba.fastjson.JSON
import org.asion.bot.parser.DetailParser
import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItemManger
import org.asion.bot.helper.RedisClientLocal
import java.util.regex.Pattern

/**
 * Tmall商品爬虫，详情
 *
 * 暂时只爬取护肤、彩妆销量前240的商品。
 *
 *
 * @author Asion
 * @since 2018-05-03
 */
class TmallItemDetailCrawler(private var captureItemManger: CaptureItemManger, private val parser: DetailParser) : WebCrawler() {

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
        return href.startsWith("https://detail.tmall.hk/item.htm")
        //&& LEAF_CATEGORIES1.matcher(href).matches()
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

        if (page.parseData is HtmlParseData
                && url.startsWith("https://detail.tmall.hk/item.htm")
        ) {
            val htmlParseData = page.parseData as HtmlParseData
            val text = htmlParseData.text
            val html = htmlParseData.html
            val links = htmlParseData.outgoingUrls

            println("Text length: " + text.length)
            println("Html length: " + html.length)
            println("Html: " + html.toString())
            println("Number of outgoing links: " + links.size)

            val pattern = Pattern.compile("([&|?]id=(\\d{11}|\\d{12})&)")
            val matcher = pattern.matcher(url)
            matcher.find()
            val originId = matcher.group(2)

            val itemString = RedisClientLocal.get(originId, 1)
            val seekItem = JSON.parseObject(itemString, CaptureItem::class.java)
            var dbIndex = 1
            if (seekItem == null) {
                seekItem == CaptureItem()
                dbIndex = 2
            }
            // 解析html获取相应的数据
            val item = parser.parse(html, seekItem)
            item.grab = 1
            item.originId = originId.toLong()
            // 保存数据到数据库
//            seekManger.save(items)
            // 保存到redis
            RedisClientLocal.set(originId, item, dbIndex)

            println(item)
        }
    }

    companion object {
        //https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.767c2420x1wsEJ&id=520928380305&skuId=3561403886285&areaId=330106&is_b=1&user_id=2541315686&cat_id=55916001&rn=6e62dabd737a68f19303c5871327973e
        /**
         * 1. 销量倒序排序：sort=d
         * 2. 小图浏览模式：style=l
         * 3. 页码简单处理，每页28*3=84条：3*84=252条
         * 4. 类目
         */
        private val LEAF_CATEGORIES1 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*&.*(sort=d)&.*(style=l)&.*""")

    }

}