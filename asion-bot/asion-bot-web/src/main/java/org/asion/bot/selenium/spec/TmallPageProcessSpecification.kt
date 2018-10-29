package org.asion.bot.selenium.spec

import org.apache.commons.lang3.StringUtils
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.selector.Html
import java.util.regex.Pattern

/**
 * @author Asion.
 * @since 2018/5/15.
 */
object TmallPageProcessSpecification {
    /**
     * 是否要处理该页面
     *
     * @param html 页面内容
     * @return true：处理，false：不处理。
     */
    fun shouldProcess(page: Page, html: Html): Boolean {
        // //*[@id="content"]/div[2]/div[1]/div[4]/div[4]/div/div[1]/div[1]/div[2]/a[1]
        val tmp = html.`$`("#content > div.j_category.category-con > div.category-inner-con.j_categoryInnerCon.j_categoryTab > div.content-con.j_categoryContent").all()
        return tmp != null && tmp.size > 0
    }

    /**
     * 是否为列表页
     *
     * @param html 页面内容
     * @return true: 符合要求的列表页，false: 不符合要求
     */
    fun isListPage(html: Html): Boolean {
        val tmp = html.`$`(".ui-page-cur").get()
        return StringUtils.isNotBlank(tmp)
    }

    /**
     * 过滤页数，只爬取相应的页数
     */
    fun filterPageNo(html: Html, pageNo: Int): Boolean {
        val currentPageNo = getCurrentPageNo(html).toLong()
        return currentPageNo <= pageNo
    }

    /**
     * 是否为详情页
     *
     * @param html 页面内容
     * @return true: 符合要求的列表页，false: 不符合要求
     */
    fun isDetailPage(html: Html): Boolean {
        val tmp = html.`$`("#J_DetailMeta").get()
        return StringUtils.isNotBlank(tmp)
    }

    /**
     * 是否为详情页
     *
     * @param html 页面内容
     * @return true: 符合要求的列表页，false: 不符合要求
     */
    fun isChinaPage(pageUrl: String, html: Html): Boolean {
        // &prop=21299:27772 pageUrl
        val tmp = html.`$`(".ui-page-cur").get()
        return StringUtils.isNotBlank(tmp) && pageUrl.contains("&prop=21299:27772")
    }

    /**
     * 列表页获取当前页码
     *
     * @param html 页面内容
     * @return 当前页码
     */
    fun getCurrentPageNo(html: Html): String {
        // //*[@id="content"]/div/div[8]/div/b[1]/b[1]
        // ui-page-num
        return html.xpath("//*[@id=\"content\"]/div/div[contains(@class,'ui-page')]/div/b[contains(@class,'ui-page-num')]/b[contains(@class,'ui-page-cur')]/text()").toString()
    }

    /**
     * 获取tmall商品源id
     *
     * @param detailLink 详情链接
     */
    fun getOriginId(detailLink: String): Long {
        val pattern = Pattern.compile("([&|?]id=(\\d{11}|\\d{12})&)")
        val matcher = pattern.matcher(detailLink)
        matcher.find()
        val originId = matcher.group(2) ?: return 0
        return originId.toLong()
    }

}
