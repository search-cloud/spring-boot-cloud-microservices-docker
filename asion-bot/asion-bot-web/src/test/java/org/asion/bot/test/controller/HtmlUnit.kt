package org.asion.bot.test.controller

import com.alibaba.fastjson.JSON
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.*
import org.asion.bot.CaptureItem
import org.asion.bot.helper.RedisClientLocal
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern


/**
 *
 * @author Asion.
 * @since 2018/5/5.
 */
class HtmlUnit {

    private fun handleDetailPage(url: String, item: CaptureItem): CaptureItem {
        webClient.addCookie("10de310f70c82953c7c14a3854d57a03", URL(url), null)
        webClient.addCookie("cxE86C6Y13UzhvYR0ZfRChCNkZwSdhYndcpPEjVlrUDK7Gw73Dm7mAATeNs3VMChRGrlM7IQKNCmZPtuC%2FDBww%3D%3D", URL(url), null)
        webClient.addCookie("AQdzn1DQ%2BzcjayTA5NAgHpq%2BGgMpzaURksPbhYh1ZLQ%3D", URL(url), null)

        var page: HtmlPage? = null

        try {
            val start = System.currentTimeMillis()
            println("startTime: $start ms")
            page = webClient.getPage(url)
            val finished = System.currentTimeMillis()
            println("get page succeed! time: ${finished - start}")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FailingHttpStatusCodeException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        // 1. 商品名称 小标题 #J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1
        val itemNameTag = page?.querySelector<HtmlHeading1>("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1")
        if (itemNameTag != null) {
            val itemName = itemNameTag.textContent
            item.itemName = itemName.trim()
        }

        // 2. 原价 #J_StrPriceModBox > dd > span 促销价：#J_PromoPrice > dd > div > span
        val marketPriceTag = page?.querySelector<HtmlSpan>("#J_StrPriceModBox > dd > span.tm-price")
        if (marketPriceTag != null) {
            val marketPrice = marketPriceTag.textContent
            item.marketPrice = marketPrice.trim()
        }

        // 3. 品牌 #J_BrandAttr > div > b
        val brandTag = page?.querySelector<HtmlBold>("#J_BrandAttr > div > b")
        var brandFlag = false
        if (brandTag != null) {
            val brandName = brandTag.textContent
            item.brandName = brandName.trim()
            brandFlag = true
        }
        // 属性li标签
        val attrTag = page?.querySelectorAll("#J_AttrUL > li")
        if (attrTag != null && attrTag.size > 0) {
            // 4. 产地 #J_AttrUL > li:nth-child(9)
            var flag = false
            for (element in attrTag) {
                val eText = element.textContent
                if (eText.contains("产地")) {
                    val country = eText.substring(eText.indexOf("产地: ") + 4)
                    item.country = country.trim()
                    flag = true
                    break
                }
            }
            if (!flag) {
                // 韩国品牌 菜鸟宁波保税3号仓发货 #J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > div.fromName
                val countryTag1 = page?.querySelector<HtmlDivision>("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > div.fromName")
                if (countryTag1 != null) {
                    val text = countryTag1.textContent
                    val country = text.trim().substring(0, text.indexOf("品牌") - 1)
                    item.country = country.trim()
                }
            }
            // 5. 规格 #J_AttrUL > li:nth-child(6) 含量
            for (element in attrTag) {
                val eText = element.textContent
                if (eText.contains("含量")) {
                    val qualification = eText.substring(eText.indexOf("含量: ") + 4)
                    item.qualification = qualification.trim()
                    break
                }
            }
            if (!brandFlag) {
                // #J_attrBrandName
                val element = page?.getElementById("#J_attrBrandName")
                if (element != null) {
                    val eText = element.textContent
                    if (eText.contains("品牌: ")) {
                        val brandName = eText.substring(eText.indexOf("品牌: ") + 4)
                        item.brandName = brandName.trim()
                    }
                }
            }
        }

        return item
    }

//    @Test
    fun testOne() {
        // https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.39e97659wpkATC&id=564584739454&skuId=3566914577999&areaId=330106&is_b=1&user_id=2549841410&cat_id=55950004&rn=f9e46af0c4ff04f6d8a7b3e2941cd1d0
        // https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.767c2420x1wsEJ&id=564834369998&skuId=3573659193728&areaId=330106&is_b=1&user_id=2166290049&cat_id=55916001&rn=6e62dabd737a68f19303c5871327973e
        val item = handleDetailPage("https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.39e97659wpkATC&id=564584739454&skuId=3566914577999&areaId=330106&is_b=1&user_id=2549841410&cat_id=55950004&rn=f9e46af0c4ff04f6d8a7b3e2941cd1d0", CaptureItem())
        println(item)
    }

    init {
        webClient.options.isThrowExceptionOnScriptError = false
//        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.cache.maxSize = 0
//        webClient.options.isCssEnabled = false
        webClient.options.isDownloadImages = false
        webClient.options.historyPageCacheLimit = 3000
    }

    companion object {
        private val webClient = WebClient(BrowserVersion.FIREFOX_52)

        @JvmStatic
        fun main(args: Array<String>) {
            HtmlUnit().grabAllDetail()
            webClient.close()
        }
    }

    fun grabAllDetail() {
        val seekItemList = mutableListOf<CaptureItem>()
        for (i in 1..42) {
            val itemString = RedisClientLocal.get("itemList$i")
            val tempList = JSON.parseArray(itemString, CaptureItem::class.java)
            seekItemList.addAll(tempList)
        }
        val pattern = Pattern.compile("([&|?]id=(\\d{11}|\\d{12})&)")
        seekItemList.forEach {
            //            println(it.detailLink)
            val matcher = pattern.matcher(it.detailLink)
            matcher.find()
            val originId = matcher.group(2)
            it.originId = originId.toLong()
            RedisClientLocal.set(originId, it, 2)
//            println(originId)
        }

        seekItemList.forEach {
            println("start id: ${it.originId}, link: ${it.detailLink}")
            val item = handleDetailPage(it.detailLink, it)
            RedisClientLocal.set(it.originId.toString(), item, 2)
            println("succeed item: $it")
        }
    }
}