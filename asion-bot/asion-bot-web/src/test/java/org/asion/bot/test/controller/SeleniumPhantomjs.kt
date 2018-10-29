package org.asion.bot.test.controller

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File


/**
 * @author Asion.
 * @since 2018/5/7.
 */
class SeleniumPhantomjs {

    /**
     * chromeDriver是谷歌的浏览器驱动，用来适配Selenium,有图形页面存在，在调试爬虫下载运行的功能的时候会相对方便
     * @author zhuangj
     * @date 2017/11/14
     */
    companion object TestChromeDriver {

        private var service: ChromeDriverService? = null

        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        // 创建一个 Chrome 的浏览器实例
        val chromeDriver: WebDriver
            get() {
//                System.setProperty("webdriver.chrome.driver", "C:/Users/sunlc/AppData/Local/Google/Chrome/Application/chrome.exe")
                System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
                service = ChromeDriverService.Builder().usingDriverExecutable(File("/usr/local/bin/chromedriver")).usingAnyFreePort().build()
                service!!.start()
                return ChromeDriver(service, DesiredCapabilities.chrome())
            }

        @JvmStatic
        fun main(args: Array<String>) {

            val driver = chromeDriver
            // 让浏览器访问 Baidu
            driver.get("https://www.taobao.com/")
            // 用下面代码也可以实现
            //driver.navigate().to("http://www.baidu.com");
            // 获取 网页的 title
            println(" Page title is: " + driver.title)
            // 通过 id 找到 input 的 DOM
            val element = driver.findElement(By.id("q"))
            // 输入关键字
            element.sendKeys("东鹏瓷砖")
            // 提交 input 所在的 form
            element.submit()
            // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
//            WebDriverWait(driver, 10).until<Any>(object : ExpectedCondition {
//                fun apply(input: Any): Any {
//                    return (input as WebDriver).title.toLowerCase().startsWith("东鹏瓷砖")
//                }
//            })
            WebDriverWait(driver, 10).until(ExpectedCondition() {
                apply {
                    (it as WebDriver).title.toLowerCase().startsWith("东鹏瓷砖")
                }
            })
            // 显示搜索结果页面的 title
            println(" Page title is: " + driver.title)
            // 关闭浏览器
            driver.quit()
            // 关闭 ChromeDriver 接口
            service!!.stop()
        }


    }

}
