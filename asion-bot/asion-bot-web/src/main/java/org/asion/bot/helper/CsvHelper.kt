package org.asion.bot.helper

import com.csvreader.CsvWriter
import edu.uci.ics.crawler4j.url.WebURL
import org.asion.bot.CaptureItem
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.regex.Pattern

/**
 *
 * @author Asion.
 * @since 2018/5/4.
 */
open class CsvHelper {

    private val logger = LoggerFactory.getLogger(CsvHelper::class.java)

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
     * 保存解析的数据到csv
     *
     * @param items 爬取的商品信息
     */
    fun saveItemToCsv(items: List<CaptureItem>) {
        items.forEach {
            try {
                itemCsvWriter = CsvWriter(FileWriter(itemCsv, true), ',')
                val item = arrayOf(it.categoryName, it.brandName, it.country, it.itemName, it.qualification, it.marketPrice, it.salePrice, it.saleNumber, it.detailLink)
                itemCsvWriter!!.writeRecord(item)
                itemCsvWriter!!.flush()
                itemCsvWriter!!.close()
            } catch (e: IOException) {
                logger.error("输出csv异常：", e)
            }
        }
    }

    /**
     * 保存访问的link
     *
     * @param webURL webURL
     * @throws IOException IOException
     */
    fun saveVisitLink(webURL: WebURL) {
        cw = CsvWriter(FileWriter(linkCsv, true), ',')
        cw!!.writeRecord(arrayOf(webURL.url))
        cw!!.flush()
        cw!!.close()
    }

    /**
     * 保存外部links
     *
     * @param webURLs webURL
     * @throws IOException IOException
     */
    fun saveOutgoingLinks(webURLs:Set<WebURL>) {
        webURLs.forEach {
            cw = CsvWriter(FileWriter(allLinksCsv, true), ',')
            cw!!.writeRecord(arrayOf(it.url))
            cw!!.flush()
            cw!!.close()
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
        private val LEAF_CATEGORIES1 = Pattern.compile("""^[a-zA-z]+://[^\s]*.*&.*(sort=d)&.*(style=l)&.*""")

        /**
         * 爬取link文件路径
         */
        private const val LINK_PATH = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data/link.csv"
        private const val LINKS_PATH = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data/all-links.csv"

        /**
         * 爬取商品信息文件路径
         */
        private const val ITEM_PATH = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data/item.csv"

        fun matchBrand(itemName: String): String {
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
            val firstSpace = itemName1.indexOf(' ')
            var secondSpace: Int
            if (firstSpace != -1) {
                secondSpace = itemName1.indexOf(' ', firstSpace + 1)
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

        @JvmStatic
        fun main(args: Array<String>) {

            val input = File("/Users/Asion/Workstation/Personal/java-workspace/spring-boot-cloud-microservices-docker/asion-search/asion-search-web/src/main/java/org/asion/search/crawler/test.html")
            val document = Jsoup.parse(input, "GBK", "https://list.tmall.hk/")
//            val document = Jsoup.parse(html)
            // 类目#J_CrumbSlideCon > li:nth-child(2) > div > a   //*[@id="J_CrumbSlideCon"]/li[1]
            val filterBox = document.getElementById("J_CrumbSlideCon")
            val categoryTags = filterBox.select("div.j_CrumbDrop > a.j_CrumbDropHd")
            val categoryNames = StringBuilder()
            if (categoryTags.size != 0) {
                categoryTags.forEachIndexed { index, categoryTag ->
                    categoryNames.append(categoryTag.text())
                    if (index != categoryTags.size - 1) {
                        categoryNames.append("->")
                    }
                }
            }
            println(categoryNames)

            val itemList = document.getElementById("J_ItemList")
            val elements = itemList.getElementsByClass("product")

            elements.forEach { itemDiv ->
                val productAttrs = itemDiv.select(".productAttrs span")
                val country = productAttrs.get(1)
                print("country: ${country.text()}")
            }
        }
    }
}