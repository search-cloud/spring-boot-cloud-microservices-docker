package org.asion.bot.helper

import org.asion.bot.CaptureItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document



/**
 *
 * @author Asion.
 * @since 2018/5/5.
 */
object JsoupGetDetail {


    fun parseOne(document: Document, item: CaptureItem): CaptureItem {

        // 0. #J_AddFavorite &id=520928380305&
//        val originIdTag = document.select("#J_AddFavorite")
//        if (originIdTag != null && originIdTag.size > 0) {
//            val attr = originIdTag.attr("data-aldurl")
//
//        }

        // 1. 商品名称 小标题 #J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1
        val itemNameTag = document.select("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1")
        if (itemNameTag != null && itemNameTag.size > 0) {
            val itemName = itemNameTag.text()
            item.itemName = itemName
        }
        // 2. 原价 #J_StrPriceModBox > dd > span 促销价：#J_PromoPrice > dd > div > span
        val marketPriceTag = document.select("#J_StrPriceModBox > dd > span")
        if (marketPriceTag != null && marketPriceTag.size > 0) {
            val marketPrice = marketPriceTag.text()
            item.marketPrice = marketPrice
        }
        // 3. 品牌 #J_BrandAttr > div > b
        val brandTag = document.select("#J_BrandAttr > div > b")
        var brandFlag = false
        if (brandTag != null && brandTag.size > 0) {
            val brandName = brandTag.text()
            item.brandName = brandName
            brandFlag = true
        }
        // 属性li标签
        val attrTag = document.select("#J_AttrUL > li")
        if (attrTag != null && attrTag.size > 0) {
            // 4. 产地 #J_AttrUL > li:nth-child(9)
            var flag = false
            for (element in attrTag) {
                val eText = element.text()
                if (eText.contains("产地")) {
                    val country = eText.substring(eText.indexOf("产地: ") + 4)
                    item.country = country
                    flag = true
                    break
                }
            }
            if (!flag) {
                // 韩国品牌 菜鸟宁波保税3号仓发货 #J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > div.fromName
                val countryTag1 = document.select("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > div.fromName")
                if (countryTag1 != null && countryTag1.size > 0) {
                    val text = countryTag1.text()
                    val country = text.trim().substring(0, text.indexOf("品牌") - 1)
                    item.country = country
                }
            }
            // 5. 规格 #J_AttrUL > li:nth-child(6) 含量
            for (element in attrTag) {
                val eText = element.text()
                if (eText.contains("含量")) {
                    val qualification = eText.substring(eText.indexOf("含量: ") + 4)
                    item.qualification = qualification
                    break
                }
            }
            if (!brandFlag) {
                // #J_attrBrandName
                val element = document.select("#J_attrBrandName")
                if (element != null && element.size > 0) {
                    val eText = element.text()
                    if (eText.contains("品牌: ")) {
                        val brandName = eText.substring(eText.indexOf("品牌: ") + 4)
                        item.brandName = brandName
                    }
                }
            }
        }

        return item
    }

    fun getDocumentByUrl(url: String): Document? {
        return if (url != "") {
            Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                    .timeout(3000000)
//                    .execute()
                    .get()
        } else {
            null
        }
    }

    /**
     * {
    "categoryName": "其他美体美容->男士护理",
    "detailLink": "https://detail.tmall.hk/item.htm?id=18806902137&skuId=79616221238&areaId=330106&is_b=1&user_id=1706787496&cat_id=55916006&q=&rn=e2d982847acd283b4b051b236920e9ed",
    "grab": 1,
    "index": 68,
    "itemName": "卓悦Ciracle稀拉克儿嫩白补水保湿紧致祛痘粉刺男士精华液105ml",
    "marketPrice": "¥249.00",
    "qualification": "净含量: 105.5ml",
    "saleNumber": "3笔",
    "salePrice": "¥249.00"
    }
     */

    @JvmStatic
    fun main(args: Array<String>) {
        val document = getDocumentByUrl("https://detail.tmall.hk/item.htm?id=18806902137&skuId=79616221238&areaId=330106&is_b=1&user_id=1706787496&cat_id=55916006&q=&rn=e2d982847acd283b4b051b236920e9ed")
        if (document != null) {
            val item = parseOne(document, CaptureItem())
            println(item)
        }
    }

}