package org.asion.bot.selenium.processor

import com.alibaba.fastjson.JSON
import com.ytx.seeker.selenium.spec.TmallPageProcessSpecification.filterPageNo
import com.ytx.seeker.selenium.spec.TmallPageProcessSpecification.getOriginId
import com.ytx.seeker.selenium.spec.TmallPageProcessSpecification.isDetailPage
import com.ytx.seeker.selenium.spec.TmallPageProcessSpecification.isListPage
import org.apache.commons.lang3.StringUtils
import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItemManger
import org.asion.bot.helper.Counter
import org.asion.bot.helper.RedisClientLocal
import org.asion.bot.selenium.webdirver.PhantomJsDriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.selector.Html
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

/**
 * @author Asion.
 * @since 2018/5/7.
 */
class TmallPageProcessor(
        private val captureItemManger: CaptureItemManger,
        // 搜索关键字
        private val keyWord: String) : PageProcessor {

    // 模拟请求header
    private val site = Site
            .me()
            .setCharset("UTF-8")
            .setCycleRetryTimes(3)
            .setSleepTime(3 * 1000)
            .addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/52.0")

    /**
     * 使用chromeDriver程序正常运行,转换成PhtanomJs后发现查询到的数据不是想要的数据，复制HTML查看页面后,
     * 发现搜索的数据是错乱的,搜索框上显示着？？？，猜测是转码的问题，经过URLEncode之后，程序正常运行。
     *
     * @return encodedUrl
     */
    private val encodedKeyWord: String
        get() {
            try {
                return URLEncoder.encode(keyWord, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return StringUtils.EMPTY
        }

    override fun getSite(): Site {
        return site
    }


    /**
     * 开始处理页面
     *
     * @param page 页面
     */
    override fun process(page: Page) {
        val driver = PhantomJsDriverFactory.getDriver(null)
        driver.get(page.request.url)

        val webElement = driver.findElement(By.id("content"))
        val str = webElement.getAttribute("outerHTML")
        val html = Html(str)
//        if (shouldProcess(page, html)) {
//            val inputStream = ClassLoader.getSystemResourceAsStream("CategoryPageContent.html")
//            val strings = IOUtils.readLines(inputStream, "GBK")
//            val htmlString = strings.joinToString("\n")
//            processPagination(page, Html.create(htmlString))
//        }

        if (isListPage(html)
                // && isChinaPage(page.request.url, html)
                && filterPageNo(html, 3)) {
            processListPage(page, html, driver)
        } else if (isDetailPage(html)) {
            processDetailPage(page, html, driver)
        } else {
            // ignore
            logger.warn("ignore url: ${page.request.url}")
        }

    }

    /**
     * 处理分页规则
     *
     * @param page 页面信息
     * @param html 页面内容
     */
    private fun processPagination(page: Page, html: Html) {
        // //*[@id="content"]/div[2]/div[1]/div[4]/div[4]/div/div[1]/div[1]/div[2]/a
//        val pageList1 = html.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[4]/div[4]/div/div[1]/div[1]/div[2]/a/@href").all()
//        val pageList2 = html.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[4]/div[4]/div/div[1]/div[2]/div[2]/a/@href").all()

        // //*[@id="j_CategoryMenuPannel"]/div[1]/div[2]/a
        val pageList1 = html.xpath("//*[@id=\"j_CategoryMenuPannel\"]/div[1]/div[2]/a/@href").all()
        val pageList2 = html.xpath("//*[@id=\"j_CategoryMenuPannel\"]/div[2]/div[2]/a/@href").all()

        pageList1.addAll(pageList2)
        val pageList = pageList1.map { it.replace("&style=g", "&style=l") }
                .map { it.replace("&sort=s", "&sort=d") }
                .map {
                    when {
                        it.contains("&active=1") -> it
                        else -> "$it&active=1"
                    }
                }
                .map {
                    when {
                        it.contains("&prop=21299:27772") -> it
                        else -> "$it&prop=21299:27772"
                    }
                }
                .map {
                    when {
                        it.contains("&style=l") -> it
                        else -> "$it&style=l"
                    }
                }
                .map {
                    when {
                        it.contains("&sort=d") -> it
                        else -> "$it&sort=d"
                    }
                }
                .map { "https:$it" }


        page.addTargetRequests(pageList)

        pageList.forEach {
            RedisClientLocal.set(it, Counter.safeAdd().get())
        }

        logger.warn("~~~~~preprocess~~~~~~$encodedKeyWord")
    }

    /**
     * 处理列表页
     *
     * @param page   页面信息
     * @param html   页面内容
     * @param driver WebDriver
     */
    private fun processListPage(page: Page, html: Html, driver: WebDriver) {
        val requestExtend = page.request as RequestExtend
        println("parentUrl:" + requestExtend.parentUrl!!)
        println("nowUrl:" + page.url)

        val detailPageList = html.xpath("//*[@id=\"mainsrp-itemlist\"]").`$`("a[id^='J_Itemlist_TLink_']").xpath("//a/@href").all()
        for (detailPage in detailPageList) {
            val extend = RequestExtend("https:$detailPage", page.url.toString())
            page.addTargetRequest(extend)
        }

        val document = html.document;
        // 类目#J_CrumbSlideCon > li:nth-child(2) > div > a   //*[@id="J_CrumbSlideCon"]/li[1]
        val filterBox = document.getElementById("J_CrumbSlideCon")
        val categoryTags = filterBox.select("div.j_CrumbDrop > a.j_CrumbDropHd")
        val categoryNames = StringBuilder()
        if (categoryTags.size == 0) {
            categoryNames.append("")
        } else {
            categoryTags.forEachIndexed { index, categoryTag ->
                categoryNames.append(categoryTag.text())
                if (index != categoryTags.size - 1) {
                    categoryNames.append("->")
                }
            }
        }

        val itemList = document.getElementById("J_ItemList")
        val elements = itemList.getElementsByClass("product")

        val items = ArrayList<CaptureItem>(elements.size)
        elements.forEachIndexed { index, itemDiv ->

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
            crawlerItem.categoryName = categoryNames.toString()
            print("categoryName: $categoryNames")
            print("\t")

            // 生产国家 //*[@id="J_ItemList"]/div[1]/div/div[2]/div/span[2]
            val productAttrs = itemDiv.select(".productAttrs span")
            if (productAttrs.size >= 2) {
                val country = productAttrs[1]
                print("country: ${country.text()}")
                print("\t")
                crawlerItem.country = country.text()
            }

            // 商品名称
            val titleWrap = itemDiv.select(".proInfo-title a")
            val itemName = titleWrap.attr("title")
            crawlerItem.itemName = itemName
            print("itemName: $itemName")
            print("\t")

            // 品牌
            if (productAttrs.size >= 1) {
                val brand = productAttrs[0]
                crawlerItem.brandName = brand.text()
                print("brandName: ${brand.text()}")
                print("\t")
            }

            // TODO 包装规格，暂时不知道取哪里的信息

            // 市场价，列表页中没有市场价，暂时使用销售价代替
            val marketPrice = itemDiv.select(".national-wrap .proSell-price")
            crawlerItem.marketPrice = marketPrice.text()
            print("marketPrice: " + marketPrice.text())
            print("\t")

            // 当前销售价
            val salePrice = itemDiv.select(".national-wrap .proSell-price")
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
            print("\t")

            items.add(crawlerItem)
            println("===============================忧伤的分线==============================")
            println("======================================================================")
        }
        // 保存数据到数据库
        captureItemManger.save(items)
        // 保存到redis
        items.forEach {
            RedisClientLocal.set(it.originId.toString(), it, 1)
        }

    }

    /**
     * 处理详情页
     *
     * @param page 页面信息
     * @param html 页面内容
     */
    private fun processDetailPage(page: Page, html: Html, webDriver: WebDriver) {
        val url = page.url.toString()
        println("detailUrl: $url")
        if (url.startsWith(TAO_BAO_DETAIL_URL_PREFIX)) {
            processTaoBaoDetailPage(page, html)
        } else if (url.startsWith(TIAN_MAO_DETAIL_URL_PREFIX)) {
            processTmallDetailPage(page, html, webDriver)
        }
    }

    /**
     * 处理淘宝详情页
     *
     * @param page 页面信息
     * @param html 页面内容
     */
    private fun processTaoBaoDetailPage(page: Page, html: Html) {
        val requestExtend = page.request as RequestExtend
        println("parentUrl:" + requestExtend.parentUrl!!)
        println("nowUrl:" + page.url)
        page.putField("price", html.xpath("//[@id=\"J_StrPrice\"]/em[2]/text()").toString())
        var shopName = html.xpath("//*[@id=\"J_ShopInfo\"]/div/div[1]/div[1]/dl/dd/strong/a/text()").toString()
        if (StringUtils.isBlank(shopName)) {
            shopName = html.xpath("//*[@id=\"header-content\"]/div[2]/div[1]/div[1]/a/text()").toString()
        }
        page.putField("shopName", shopName)
        page.putField("title", html.xpath("////*[@id=\"J_Title\"]/h3/text()").toString())

        //        po.setParentUrl(requestExtend.getParentUrl());
        //        po.setPrice(page.getResultItems().get("price"));
        //        po.setShopName(page.getResultItems().get("shopName"));
        //        po.setTitle(page.getResultItems().get("title"));
        //        po.setUrl(page.getUrl().toString());
        //        taoBaoCrawlerService.saveProductDetail(po);
    }

    /**
     * 处理天猫详情页
     *
     * @param page   页面信息
     * @param html   页面内容
     * @param driver selenium.WebDriver
     */
    private fun processTmallDetailPage(page: Page, html: Html, driver: WebDriver) {
//        page.putField("price", html.xpath("//[@id=\"J_StrPriceModBox\"]/dd/span/text()").toString())
//        page.putField("shopName", driver.findElement(By.name("seller_nickname")).getAttribute("value"))
//        page.putField("name", html.xpath("//[@id=\"J_DetailMeta\"]/div[1]/div[1]/div/div[1]/h1/text()").toString())

        val originId = getOriginId(page.request.url)

        var dbIndex = 1
        val itemString = RedisClientLocal.get(originId.toString(), dbIndex)
        var item = JSON.parseObject(itemString, CaptureItem::class.java)
        if (item == null) {
            item = CaptureItem()
            dbIndex = 2
        }

        val document = html.document
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

        // 保存数据到数据库
        captureItemManger.save(item)
        // 保存到redis
        RedisClientLocal.set(item.originId.toString(), item, dbIndex)
    }

    companion object {

        private val logger = LoggerFactory.getLogger(TmallPageProcessor::class.java)
        // 淘宝详情页 url prefix
        private const val TAO_BAO_DETAIL_URL_PREFIX = "https://item.taobao.com/item.htm"
        // 天猫详情页 url prefix
        private const val TIAN_MAO_DETAIL_URL_PREFIX = "https://detail.tmall.com/item.htm"
    }

}