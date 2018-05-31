package org.asion.bot.parser

import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItem.Source.TMALL
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.util.*

/**
 *
 * @author Asion.
 * @since 2018/5/18.
 */
@Component
class WebDriverListParser {

    fun parse(driver: PhantomJSDriver): List<CaptureItem> {
        val itemList = findElement(driver, By.id("J_ItemList")) ?: return emptyList()

        val elements = findElements(driver, itemList, By.className("product"))
        val categoryNames = parseCategories(driver)

        val items = ArrayList<CaptureItem>(elements.size)
        elements.forEachIndexed { index, itemDiv ->

//            println("===============================华丽的分割线==============================")
            val crawlerItem = CaptureItem()
            crawlerItem.index = index
            crawlerItem.grab = 0

            print("Number: $index")
            print("\t")

            // originId
            val originId = itemDiv.getAttribute("data-id")
            if (originId != null && originId.isNotBlank()) {
                crawlerItem.originId = originId.toLong()
            }
            print("originId: $originId")
            print("\t")

            // 类目
            crawlerItem.categoryName = categoryNames
            print("categoryName: $categoryNames")
            print("\t")

            // 生产国家 //*[@id="J_ItemList"]/div[1]/div/div[2]/div/span[2] #J_ItemList > div:nth-child(7) > div > div.product-limited > div > span:nth-child(1) > a
            val productAttrs = findElements(driver, itemDiv, By.cssSelector(".productAttrs > span > a"))
            if (productAttrs.isNotEmpty()) {
                var country = ""
                var brand = ""
                productAttrs.forEach {
                    val title = it.getAttribute("title")
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
            val titleWrap = findElement(driver, itemDiv, By.cssSelector(".proInfo-title a"))
            if (titleWrap != null) {
                crawlerItem.itemName = titleWrap.getAttribute("title")
                // 商品链接
                val link = titleWrap.getAttribute("href")
                if (link != null && link != "") {
                    crawlerItem.detailLink = "https:$link"
                }
            }
            print("itemName: ${crawlerItem.itemName}")
            print("\t")

            // TODO 包装规格，暂时不知道取哪里的信息

            // 市场价，列表页中没有市场价，暂时使用销售价代替
            // 当前销售价 //*[@id="J_ItemList"]/div[1]/div/div[1]/p[1]/em
            val salePrice = findElement(driver, itemDiv, By.cssSelector("em.proSell-price"))
            if (salePrice != null) {
                crawlerItem.marketPrice = salePrice.text
                crawlerItem.salePrice = salePrice.text
            }
            print("marketPrice: ${crawlerItem.marketPrice}")
            print("\t")

            print("salePrice: ${crawlerItem.salePrice}")
            print("\t")

            // 销量
            val saleNumber = findElement(driver, itemDiv, By.cssSelector(".productStatus em"))
            if (saleNumber != null) {
                crawlerItem.saleNumber = saleNumber.text
            }
            print("saleNumber: ${crawlerItem.saleNumber}")
            print("\t")

            print("link: ${crawlerItem.detailLink}")
            print("\t")

            crawlerItem.source = TMALL.sourceId
            crawlerItem.sourceName = TMALL.name

            items.add(crawlerItem)
            println()
            println("===============================忧伤的分割线==============================")
        }

        return items
    }

    /**
     * 列表中解析分类
     */
    fun parseCategories(driver: PhantomJSDriver): String {
        // 类目#J_CrumbSlideCon > li:nth-child(2) > div > a   //*[@id="J_CrumbSlideCon"]/li[1]
        // #J_CrumbSlideCon > li:nth-child(3) > div > a //*[@id="J_CrumbSlideCon"]/li/a
        val filterBox = driver.findElementById("J_CrumbSlideCon")
//        val categoryTags = filterBox.select("div.j_CrumbDrop > a.j_CrumbDropHd")
        val categoryTags = filterBox.findElements(By.cssSelector("a"))
        val categoryNames = StringBuilder()
        if (categoryTags.size == 0) {
            categoryNames.append("")
        } else {
            categoryTags.forEachIndexed { index, categoryTag ->
                val attr = categoryTag.getAttribute("title")
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

    fun findElement(driver: WebDriver, by: By): WebElement? {
        return findElement(driver, by, 10)
    }

    fun findElement(driver: WebDriver, by: By, timeoutInSeconds: Long): WebElement? {
        try {
            if (timeoutInSeconds > 0) {
                val wait = WebDriverWait(driver, timeoutInSeconds)
                return wait.until { it.findElement(by) }
            }
            return driver.findElement(by)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun findElement(driver: WebDriver, element: WebElement, by: By): WebElement? {
        return findElement(driver, element, by, 10)
    }

    fun findElement(driver: WebDriver, element: WebElement, by: By, timeoutInSeconds: Long): WebElement? {
        try {
            if (timeoutInSeconds > 0) {
                val wait = WebDriverWait(driver, timeoutInSeconds)
                return wait.until { element.findElement(by) }
            }
            return element.findElement(by)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun findElements(driver: WebDriver, element: WebElement, by: By): List<WebElement> {
        return findElements(driver, element, by, 10)
    }

    fun findElements(driver: WebDriver, element: WebElement, by: By, timeoutInSeconds: Long): List<WebElement> {
        try {
            if (timeoutInSeconds > 0) {
                val wait = WebDriverWait(driver, timeoutInSeconds)
                return wait.until { element.findElements(by) } ?: return emptyList()
            }
            return element.findElements(by) ?: return emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }
}