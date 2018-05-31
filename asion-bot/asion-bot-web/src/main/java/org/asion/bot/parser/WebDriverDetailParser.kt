package org.asion.bot.parser

import org.asion.bot.CaptureItem
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component

/**
 *
 * @author Asion.
 * @since 2018/5/18.
 */
@Component
class WebDriverDetailParser {

    fun parse(item: CaptureItem, driver: PhantomJSDriver): CaptureItem {

        val itemDetailTag = findElement(driver, By.cssSelector("#J_DetailMeta"))
        println("itemDtail: $itemDetailTag")

        // 1. 商品名称 小标题 #J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1
        // //*[@id="J_DetailMeta"]/div[1]/div[1]/div/div[1]/h1
        val itemNameTag = findElement(driver, By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1"))
        if (itemNameTag != null) {
            val itemName = itemNameTag.text
            item.itemName = itemName
        }

        val priceBox = findElement(driver, By.id("J_StrPriceModBox"))
        println("priceBox: $priceBox")

        // 2. 原价 #J_StrPriceModBox > dd > span //*[@id="J_StrPriceModBox"]/dd/span
        // 促销价：#J_PromoPrice > dd > div > span //*[@id="J_PromoPrice"]/dd/div/span
        val marketPriceTag = findElement(driver, By.xpath("//*[@id=\"J_StrPriceModBox\"]/dd/span"))
        if (marketPriceTag != null) {
            val marketPrice = marketPriceTag.text
            item.marketPrice = marketPrice
        }
        // 3. 品牌 #J_BrandAttr > div > b //*[@id="J_BrandAttr"]/div/b
        val brandTag = driver.findElementByCssSelector("#J_BrandAttr > div > b")
        var brandFlag = false
        if (brandTag != null) {
            val brandName = brandTag.text
            item.brandName = brandName
            brandFlag = true
        }
        // 属性li标签
        val attrTag = driver.findElementsByCssSelector("#J_AttrUL > li")
        if (attrTag != null && attrTag.size > 0) {
            // 4. 产地 #J_AttrUL > li:nth-child(9)
            var flag = false
            for (element in attrTag) {
                val eText = element.text
                if (eText.contains("产地")) {
                    val country = eText.substring(eText.indexOf("产地: ") + 4)
                    item.country = country
                    flag = true
                    break
                }
            }
            if (!flag) {
                // 韩国品牌 菜鸟宁波保税3号仓发货 #J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > div.fromName
                val countryTag1 = driver.findElementByCssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > div.fromName")
                if (countryTag1 != null) {
                    val text = countryTag1.text
                    val country = text.trim().substring(0, text.indexOf("品牌") - 1)
                    item.country = country
                }
            }
            // 5. 规格 #J_AttrUL > li:nth-child(6) 含量
            for (element in attrTag) {
                val eText = element.text
                if (eText.contains("含量")) {
                    val qualification = eText.substring(eText.indexOf("含量: ") + 4)
                    item.qualification = qualification
                    break
                }
            }
            if (!brandFlag) {
                // #J_attrBrandName
                val element = driver.findElementById("J_attrBrandName")
                if (element != null) {
                    val eText = element.getAttribute("title")
                    if (eText.contains("品牌: ")) {
                        val brandName = eText.substring(eText.indexOf("品牌: ") + 4)
                        item.brandName = brandName
                    }
                }
            }
        }
        return item
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
}