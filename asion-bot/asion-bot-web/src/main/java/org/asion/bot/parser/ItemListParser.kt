package org.asion.bot.parser

import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItem.Source.TMALL
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.*

/**
 *
 * @author Asion.
 * @since 2018/5/4.
 */
@Component
class ItemListParser : Parser<CaptureItem> {

    override fun parse(html: String): List<CaptureItem> {
        val document = Jsoup.parse(html)

        if (filterPageNo(document, 3)) {

            val categoryNames = parseCategories(document)

            val itemList = document.getElementById("J_ItemList")
            val elements = itemList.getElementsByClass("product")

            val items = ArrayList<CaptureItem>(elements.size)
            elements.forEachIndexed { index, itemDiv ->

                print("Number: $index \t")

                val crawlerItem = CaptureItem()
                crawlerItem.index = index
                crawlerItem.grab = 0

                // originId
                val originId = itemDiv.attr("data-id")
                if (originId != null && originId.isNotBlank()) {
                    crawlerItem.originId = originId.toLong()
                }
                print("originId: $originId")
                print("\t")

                // 类目
                crawlerItem.categoryName = categoryNames
                print("categoryName: $categoryNames")
                print("\t")

                // 生产国家 //*[@id="J_ItemList"]/div[1]/div/div[2]/div/span[2]
                val productAttrs = itemDiv.select(".productAttrs span")
                if (productAttrs.size > 0) {
                    var country = ""
                    var brand = ""
                    productAttrs.forEach {
                        val title = it.attr("title")
                        if (title.contains("产地：")) {
                            country = title
                        }
                        // 品牌
                        if (title.contains("品牌：")) {
                            brand = title
                        }
                    }

                    crawlerItem.country = country
                    print("country: $country")
                    print("\t")

                    crawlerItem.brandName = brand
                    print("brandName: $brand")
                    print("\t")
                }

                // 商品名称
                val titleWrap = itemDiv.select(".proInfo-title a")
                val itemName = titleWrap.attr("title")
                crawlerItem.itemName = itemName
                print("itemName: $itemName")
                print("\t")

                // TODO 包装规格，暂时不知道取哪里的信息

                // 市场价，列表页中没有市场价，暂时使用销售价代替
//                val marketPrice = itemDiv.select(".national-wrap .proSell-price")
                val salePrice = itemDiv.select("em.proSell-price")
                crawlerItem.marketPrice = salePrice.text()
                print("marketPrice: " + salePrice.text())
                print("\t")

                // 当前销售价 //*[@id="J_ItemList"]/div[1]/div/div[1]/p[1]/em
                crawlerItem.salePrice = salePrice.text()
                print("salePrice: " + salePrice.text())
                print("\t")

                // 销量
                val saleNumber = itemDiv.select(".productStatus em")
                crawlerItem.saleNumber = saleNumber.text()
                print("saleNumber: " + saleNumber.text())
                print("\t")

                // 商品链接
                crawlerItem.detailLink = "https:" + titleWrap.attr("href").trim { it <= ' ' }
                print("link: ${crawlerItem.detailLink}")

                crawlerItem.source = TMALL.sourceId
                crawlerItem.sourceName = TMALL.name

                items.add(crawlerItem)
                println()
                println("===============================忧伤的分割线==============================")
            }

            return items
        }
        return arrayListOf()
    }

    /**
     * 列表中解析分类
     */
    fun parseCategories(document: Document): String {
        // 类目#J_CrumbSlideCon > li:nth-child(2) > div > a   //*[@id="J_CrumbSlideCon"]/li[1]
        // #J_CrumbSlideCon > li:nth-child(3) > div > a //*[@id="J_CrumbSlideCon"]/li/a
        val filterBox = document.getElementById("J_CrumbSlideCon")
//        val categoryTags = filterBox.select("div.j_CrumbDrop > a.j_CrumbDropHd")
        val categoryTags = filterBox.select("a")
        val categoryNames = StringBuilder()
        if (categoryTags.size == 0) {
            categoryNames.append("")
        } else {
            categoryTags.forEachIndexed { index, categoryTag ->
                val attr = categoryTag.attr("title")
                if ("" != attr) {
                    categoryNames.append(attr)
                    if (index != categoryTags.size - 1) {
                        categoryNames.append("->")
                    }
                }
            }
        }
        return categoryNames.toString()
    }

    /**
     * 过滤页数，只爬取相应的页数
     */
    fun filterPageNo(document: Document, pageNo: Int): Boolean {
        val currentPageNo = getCurrentPageNo(document)
        println("currentPage: $currentPageNo")
        return "" == currentPageNo || currentPageNo.toLong() <= pageNo
    }

    /**
     * 列表页获取当前页码
     *
     * @param html 页面内容
     * @return 当前页码
     */
    fun getCurrentPageNo(document: Document): String {
        // //*[@id="content"]/div/div[8]/div/b[1]/b[1]
        // ui-page-num
//        return document.select("//*[@id=\"content\"]/div/div[contains(@class,'ui-page')]/div/b[contains(@class,'ui-page-num')]/b[contains(@class,'ui-page-cur')]/text()").toString()
//        #content > div > div.ui-page > div > b.ui-page-num > b.ui-page-cur
        return document.select("#content > div > div.ui-page > div > b.ui-page-num > b.ui-page-cur").text()
    }

}