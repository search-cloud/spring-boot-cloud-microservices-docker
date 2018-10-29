package org.asion.bot.selenium.webdirver

import org.openqa.selenium.By
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

/**
 *
 * @author Asion.
 * @since 2018/5/16.
 */
object ChromeDriverFactory {
//    private var service: ChromeDriverService? = null
    private val capabilities: DesiredCapabilities = DesiredCapabilities.chrome()

    init {
        capabilities.browserName = "chrome"
        capabilities.platform = Platform.MAC
        capabilities.version = "65"
        // ssl证书支持
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
        // 截屏支持
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, false)
        // css搜索支持
        capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true)
        // js支持
        capabilities.isJavascriptEnabled = true
        // 设置代理
//        val proxy = Proxy().setProxyType(Proxy.ProxyType.MANUAL).setHttpProxy("121.232.144.251:9000").setSslProxy("121.232.144.251:9000")
//        capabilities.setCapability(CapabilityType.PROXY, proxy)
    }

    // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
    // 创建一个 Chrome 的浏览器实例
    val chromeDriver: WebDriver
        get() {
//            System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome")
//            service = ChromeDriverService.Builder().usingDriverExecutable(File("/usr/local/bin/chromedriver")).usingAnyFreePort().build()
//            service!!.start()
            return RemoteWebDriver(URL("http://localhost:3434/wd/hub"), capabilities)
        }

    /**
     * 一个简单的例子
     */
    @JvmStatic
    fun main(args: Array<String>) {

        val driver = chromeDriver
        // 让浏览器访问
        driver.get("https://list.tmall.com/search_product.htm?abbucket=&active=1&acm=201603078.1003.2.1311817&sort=d&industryCatId=50029230&spm=3.7396704.20000019.20.IcTI94&abtest=&pos=18&cat=50031558&from=sn_1_rightnav&style=l&search_condition=23&scm=1003.2.201603078.OTHER_1481667949389_1311817&aldid=86940&prop=21299:27772")

        // 获取商品列表元素
        val element = driver.findElement(By.id("J_ItemList"))

//        // 模拟点击销量
//        val de = driver.findElement(By.xpath("//*[@id=\"J_Filter\"]/a[4]"))
//        de.click()
//
//        WebDriverWait(driver, 10).until(ExpectedCondition {
//            (it as WebDriver).findElement(By.id("J_ItemList")).getAttribute("class").isNotBlank()
//        })
//
//        println("large view: ${element.getAttribute("class")}")
//        // 模拟点击小图模式
//        val le = driver.findElement(By.xpath("//*[@id=\"J_Filter\"]/a[8]"))
//        le.click()
        WebDriverWait(driver, 10).until(ExpectedCondition {
            (it as WebDriver).findElement(By.id("J_ItemList")).getAttribute("class").equals("listItem lv5 clearfix")
        })

        println("small view: ${element.getAttribute("class")}")

        // 模拟点击产地中国

        driver.get("https://list.tmall.com/search_product.htm?spm=a220m.1000858.0.0.dhqe9V&pos=17&cat=50031557&active=1&style=l&from=sn_1_rightnav&acm=201603078.1003.2.1311817&sort=d&search_condition=23&scm=1003.2.201603078.OTHER_1481435874848_1311817&industryCatId=50029231&prop=21299:27772")

        // 获取商品列表元素
        val element1 = driver.findElement(By.id("J_ItemList"))
        WebDriverWait(driver, 10).until(ExpectedCondition {
            (it as WebDriver).findElement(By.id("J_ItemList")) != null
        })

        println("small view: ${element.getAttribute("class")}")

        // 用下面代码也可以实现
        //driver.navigate().to("https://www.taobao.com/");
//        // 获取 网页的 title
//        println(" Page title is: " + driver.title)
//        // 通过 id 找到 input 的 DOM
//        val element = driver.findElement(By.id("mq"))
//        // 输入关键字
//        element.sendKeys("dubbo")
//        // 提交 input 所在的 form
//        element.submit()
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
//        WebDriverWait(driver, 5).until(ExpectedCondition {
//            (it as WebDriver).title.toLowerCase().startsWith("dubbo")
//        })
        // 显示搜索结果页面的 title
//        println(" Page title is: " + driver.title)
        // 关闭浏览器当前标签
        driver.close()
        // 关闭 ChromeDriver 接口
//        service!!.stop()
    }

}