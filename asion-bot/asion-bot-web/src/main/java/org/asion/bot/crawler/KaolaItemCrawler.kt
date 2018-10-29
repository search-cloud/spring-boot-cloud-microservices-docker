package org.asion.bot.crawler

import com.csvreader.CsvWriter
import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import org.asion.bot.CaptureItem
import org.jsoup.Jsoup
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

/**
 * 考拉网商品爬虫
 *
 * 暂时只爬取护肤、彩妆销量前240的商品。
 *
 *
 * @author Asion
 * @since 2018-04-21
 */
class KaolaItemCrawler : WebCrawler() {

    /**
     * 保存链接的csv文件
     */
    private val linkCsv: File

    /**
     * 保存页面上所有出站链接的csv文件
     */
    private val allLinksCsv: File

    /**
     * 保存商品信息的csv文件
     */
    private val itemCsv: File

    /**
     * CsvWriter
     */
    private var cw: CsvWriter? = null

    /**
     * CsvWriter
     */
    private var allLinksCw: CsvWriter? = null

    /**
     * ItemCsvWriter
     */
    private var itemCsvWriter: CsvWriter? = null

    init {
        linkCsv = File(LINK_PATH)
        allLinksCsv = File(LINKS_PATH)
        itemCsv = File(ITEM_PATH)
        var append = false
        if (linkCsv.exists() && linkCsv.isFile) {
            val delete = linkCsv.delete()
            if (delete) append = true
        }
        cw = CsvWriter(FileWriter(linkCsv, append), ',')
        cw!!.write("请求路径")
        cw!!.close()

        var appends = false
        if (allLinksCsv.exists() && allLinksCsv.isFile) {
            val delete = allLinksCsv.delete()
            if (delete) appends = true
        }
        allLinksCw = CsvWriter(FileWriter(allLinksCsv, appends), ',')
        allLinksCw!!.write("所有外部链接")
        allLinksCw!!.close()

        var itemAppend = false
        if (itemCsv.exists() && itemCsv.isFile) {
            val delete = itemCsv.delete()
            if (delete) itemAppend = true
        }
        itemCsvWriter = CsvWriter(FileWriter(itemCsv, itemAppend), ',')

        // 三级类目	品牌	产地	名称	规格	原价	促销价	销量	链接
        itemCsvWriter!!.writeRecord(arrayOf("类目", "品牌", "产地", "名称", "规格", "原价", "促销价", "销量", "链接"))
        itemCsvWriter!!.close()
    }

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
        var visitFlag = false
        if (href.startsWith("https://www.kaola.com")
                // 护肤
                // 彩妆
                && (LEAF_CATEGORIES1.matcher(href).matches() || LEAF_CATEGORIES2.matcher(href).matches())
        ) {
            visitFlag = true
            try {
                linkToCsv(url)
            } catch (e: IOException) {
                logger.error("导出链接到csv异常：", e)
            }

        }
        return visitFlag
    }

    override fun handleUrlBeforeProcess(curURL: WebURL): WebURL {
        var href = curURL.url
        if (href.contains("&isDesc=true") && href.contains("&#topTab")) {
            return curURL
        }
        if (href.contains("&isDesc=false")) {
            href = href.replace("&isDesc=false", "&isDesc=true")
        } else if (!href.contains("&isDesc=true") && !href.contains("&isDesc=false")) {
            href += "&isDesc=true"
        }
        if (!href.contains("&#topTab")) {
            href += "&#topTab"
        }

        curURL.url = href
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
            linksToCsv(links)

            println("Text length: " + text.length)
            println("Html length: " + html.length)
            println("Number of outgoing links: " + links.size)

            val document = Jsoup.parse(html)

            // 类目
            val filterBox = document.getElementById("filterbox")
            val categoryTags = filterBox.select(".resultinfo a")
            val categoryNames = StringBuilder()
            categoryTags.filter { "全部结果" != it.text() }.forEachIndexed { index, categoryTag ->
                categoryNames.append(categoryTag.text())
                if (index != categoryTags.size - 2) {
                    categoryNames.append("->")
                }
            }

            val result = document.getElementById("result")
            val elements = result.children()

            val kaolaItems = ArrayList<CaptureItem>(elements.size)
            elements.forEach { element ->
                val ui = Jsoup.parse(element.toString())

                val kaolaItem = CaptureItem()
                // 类目
                kaolaItem.categoryName = categoryNames.toString()
                print("categoryName: ${categoryNames.toString()}")
                print("\t")

                // 生成国家
                val country = ui.select(".goodsinfo span")
                print("country: ${country.text()}")
                print("\t")
                kaolaItem.country = country.text()

                // 商品名称
                val titleWrap = ui.select(".titlewrap")
                val title = titleWrap.select("a h2")
                val itemName = title.text()
                kaolaItem.itemName = itemName
                print("itemName: $itemName")
                print("\t")

                // TODO 品牌，研究发现商品名称前面两个空格分隔的字符为品牌，暂时截取商品名称前面字符代替
                val brand = matchBrand(itemName)
                kaolaItem.brandName = brand
                print("brandName: $brand")
                print("\t")

                // TODO 包装规格，暂时不知道去哪里的信息

                // 市场价
                val marketPrice = ui.select(".price .marketprice")
                kaolaItem.marketPrice = marketPrice.text()
                print("marketPrice: " + marketPrice.text())
                print("\t")

                // 当前销售价
                val salePrice = ui.select(".price .cur")
                kaolaItem.salePrice = salePrice.text()
                print("salePrice: " + salePrice.text())
                print("\t")

                // 销量
                val saleNumber = ui.select(".goodsinfo a")
                kaolaItem.saleNumber = saleNumber.text()
                print("saleNumber: " + saleNumber.text())
                print("\t")

                // 商品链接
                val itemLink = ui.select(".goodswrap a")
                kaolaItem.detailLink = "https://www.kaola.com/" + itemLink.attr("href").trim { it <= ' ' }
                print("link: ${kaolaItem.detailLink}")
                print("\t")


                kaolaItems.add(kaolaItem)
                println("===============================忧伤的分线==============================")
                println("======================================================================")
            }


            saveItemToCsv(kaolaItems)
        }
    }

    private fun saveItemToCsv(kaolaItems: List<CaptureItem>) {
        kaolaItems.forEach { kaolaItem ->
            try {
                itemCsvWriter = CsvWriter(FileWriter(itemCsv, true), ',')
                val item = arrayOf(kaolaItem.categoryName, kaolaItem.brandName, kaolaItem.country, kaolaItem.itemName, kaolaItem.qualification, kaolaItem.marketPrice, kaolaItem.salePrice, kaolaItem.saleNumber, kaolaItem.detailLink)
                itemCsvWriter!!.writeRecord(item)
                itemCsvWriter!!.flush()
                itemCsvWriter!!.close()
            } catch (e: IOException) {
                logger.error("输出csv异常：", e)
            }
        }
    }

    /**
     * 保存link
     *
     * @param webURL webURL
     * @throws IOException IOException
     */
    private fun linkToCsv(webURL: WebURL) {
        cw = CsvWriter(FileWriter(linkCsv, true), ',')
        cw!!.writeRecord(arrayOf(webURL.url))
        cw!!.flush()
        cw!!.close()
    }

    /**
     * 保存links
     *
     * @param webURLs webURL
     * @throws IOException IOException
     */
    private fun linksToCsv(webURLs: Set<WebURL>) {
        webURLs.forEach {
            cw = CsvWriter(FileWriter(allLinksCsv, true), ',')
            cw!!.writeRecord(arrayOf(it.url))
            cw!!.flush()
            cw!!.close()
        }
    }

    companion object {

        private val categoryIds = "550|546"

        //    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");

        /**
         * 销量倒序排序
         * 根据类目搜索
         * 页码简单处理，只需要4页，每页60条：4*60=240条
         * changeContent是根据类目的
         */
        //    private final static Pattern LEAF_CATEGORIES =
        //            Pattern.compile("^[a-zA-z]+://[^\\s]*.*(pageNo=([1234])).*(sortfield=2).*(&headCategoryId=550|&headCategoryId=546).*((&changeContent=c)|(&changeContent=crumbs_c)|(&changeContent=crumbs_0))$.*");
//        private val LEAF_CATEGORIES1 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*(pageNo=[1234])&.*(sortfield=2)&.*(&headCategoryId=550|&headCategoryId=546).*((&changeContent=)$)?(c|crumbs_c|crumbs_0|(&#topTab))$.*$""")
        private val LEAF_CATEGORIES1 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*(pageNo=[1234])&.*(sortfield=2)&.*((&headCategoryId=)550|546|735|736|725|734|751|762|739|5521).*(((&changeContent=)(c|crumbs_c|crumbs_0))|(&#topTab))$.*""")
        /**
         * changeContent没有的情况
         */
//        private val LEAF_CATEGORIES2 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*(pageNo=[1234])&.*(sortfield=2)&.*((&headCategoryId=550|&headCategoryId=546)|(&#topTab))$.*$""")
        private val LEAF_CATEGORIES2 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*(pageNo=[1234])&.*(sortfield=2)&.*(((&headCategoryId=)550|546|735|736|725|734|751|762|739|5521)|(&#topTab))$.*""")

        /**
         * 爬取link文件路径
         */
        private const val LINK_PATH = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data/crawl/link.csv"
        private const val LINKS_PATH = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data/crawl/all-links.csv"

        /**
         * 爬取商品信息文件路径
         */
        private const val ITEM_PATH = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data/crawl/item.csv"

        @JvmStatic
        fun main(args: Array<String>) {

            arrayOf("自然共和国|NatureRepublic自然共和国芦荟舒缓保湿喷雾2支装自然舒缓鲜爽保湿"
                    , "【断货之王 迪丽热巴同款 】7色可选 | L'ORÉAL 欧莱雅 纷泽琉金唇膏 3克/支"
                    , "the SAEM 得鲜 爱可按钮唇膏 M系列 哑光系列"
                    , "【报销税费 原16号豆沙色】Manonde梦妆 花心娇柔丝绒唇膏笔 11豆沙色 2.5克/支"
            ).forEach {
                matchBrand(it)
            }

        }

        private fun matchBrand(itemName: String): String {
            var itemName1 = itemName
            var start = itemName1.indexOf(" | ")
            start = if (start != -1) {
                start + 3
            } else {
                start = itemName1.indexOf('】')
                if (start != -1) {
                    start + 1
                } else {
                    0
                }
            }
            itemName1 = itemName1.substring(start, itemName1.length - 1)

            val brand: String
            val firstSpace = itemName1.indexOf(' ');
            var secondSpace: Int
            if (firstSpace != -1) {
                secondSpace = itemName1.indexOf(' ', firstSpace + 1);
                secondSpace = if (secondSpace != -1) {
                    secondSpace
                } else {
                    firstSpace
                }
                brand = itemName1.substring(0, secondSpace)
            } else {
                val pattern = Pattern.compile("[[^\\x00-\\xff]|\\s]*([A-Z]\\s?[a-zA-Z]*)\\s?[[^\\x00-\\xff]|\\s\\w./[a-zA-Z]]*")
                val matcher = pattern.matcher(itemName1)
                matcher.find()
                brand = matcher.group(1)
                println("brand: $brand")
            }
            println("brandName: $brand")
            return brand
        }
    }

}