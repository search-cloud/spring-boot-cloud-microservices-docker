package org.asion.bot.selenium.webdirver

import org.asion.bot.selenium.HostAndPort
import org.openqa.selenium.Cookie
import org.openqa.selenium.Platform
import org.openqa.selenium.Proxy
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.BrowserType
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.util.*

/**
 * PhantomJs是一个基于webkit内核的无头浏览器，即没有UI界面，即它就是一个浏览器，只是其内的点击、翻页等人为相关操作需要程序设计实现;
 * 因为爬虫如果每次爬取都调用一次谷歌浏览器来实现操作,在性能上会有一定影响,而且连续开启十几个浏览器简直是内存噩梦,
 * 因此选用phantomJs来替换chromeDriver
 * PhantomJs在本地开发时候还好，如果要部署到服务器，就必须下载linux版本的PhantomJs,相比window操作繁琐
 *
 * @author Asion.
 * @since 2018/5/7.
 */
object PhantomJsDriverFactory {

    public const val PHANTOMJS_EXECUTABLE_PATH = "/Users/Asion/Workstation/Tools/phantomjs-2.1.1-macosx/bin/phantomjs"
//    public const val PHANTOMJS_GHOSTDRIVER_PATH = "/Users/Asion/Workstation/Personal/java-workspace/ytx-seeker/ytx-seeker-web/src/main/resources/templates/crawl.js"

    // 设置必要参数
    private val capabilities = DesiredCapabilities()

    init {
        capabilities.browserName = BrowserType.FIREFOX
        capabilities.platform = Platform.EL_CAPITAN
        capabilities.version = "65"
        // ssl证书支持
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
        // 截屏支持
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, false)
        // css搜索支持
        capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true)
        // js支持
        capabilities.isJavascriptEnabled = true
        // 驱动支持
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_EXECUTABLE_PATH)
        //
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, PHANTOMJS_GHOSTDRIVER_PATH)
        //
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, "--load-images=false")
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, "--load-images=false")
    }

    fun getDriver(hostAndPort: HostAndPort?): PhantomJSDriver {
        // 设置代理
        if (hostAndPort != null) {
            val proxy = Proxy().setProxyType(Proxy.ProxyType.MANUAL).setHttpProxy("${hostAndPort.ip}:${hostAndPort.port}")
            capabilities.setCapability(CapabilityType.PROXY, proxy)
        }
        val phantomJSDriver = PhantomJSDriver(capabilities)
        val cna = Cookie("cna", "", ".tmall.com", "/", Date())
        phantomJSDriver.manage().addCookie(cna)
        return phantomJSDriver
    }

}