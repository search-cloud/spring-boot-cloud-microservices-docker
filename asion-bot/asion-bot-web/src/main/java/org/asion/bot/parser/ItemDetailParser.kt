package org.asion.bot.parser

import org.asion.bot.CaptureItem
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.util.regex.Pattern

/**
 *
 * @author Asion.
 * @since 2018/5/4.
 */
@Component
class ItemDetailParser : DetailParser {

    override fun parse(html: String, item: CaptureItem): CaptureItem {

        val document = Jsoup.parse(html)

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

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val s = " 韩国品牌 菜鸟宁波保税3号仓发货"
            val substring = s.trim().substring(0, s.indexOf("品牌") - 1)
            println(substring)

            val eText = "产地: 泰国"
            val e = eText.substring(eText.indexOf("产地: ") + 4)
            println(e)
//
// &id=520928380305&
            val pattern = Pattern.compile("([&|?]id=(\\d{12})&)")
            val url = "https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.767c2420x1wsEJ&id=520928380305&skuId=3561403886285&areaId=330106&is_b=1&user_id=2541315686&cat_id=55916001&rn=6e62dabd737a68f19303c5871327973e"
            //"https://detail.tmall.hk/item.htm?id=534668325889&skuId=3190664950843&areaId=330106&is_b=1&user_id=1984927845&cat_id=55940001&rn=d1a0bc2a1022a67308d9b974c474b52b"
            val matcher = pattern.matcher(url)
            matcher.find()
            val originId = matcher.group(2)
            println("originId: $originId")
        }
    }
}