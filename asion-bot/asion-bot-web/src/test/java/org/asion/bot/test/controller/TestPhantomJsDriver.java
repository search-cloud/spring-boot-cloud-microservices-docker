package org.asion.bot.test.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * PhantomJs是一个基于webkit内核的无头浏览器，即没有UI界面，即它就是一个浏览器，只是其内的点击、翻页等人为相关操作需要程序设计实现;
 * 因为爬虫如果每次爬取都调用一次谷歌浏览器来实现操作,在性能上会有一定影响,而且连续开启十几个浏览器简直是内存噩梦,
 * 因此选用phantomJs来替换chromeDriver
 * PhantomJs在本地开发时候还好，如果要部署到服务器，就必须下载linux版本的PhantomJs,相比window操作繁琐
 * @author Asion
 * @since 2017/11/14
 */
public class TestPhantomJsDriver {


    public static PhantomJSDriver getPhantomJSDriver(){
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", false);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/Users/Asion/Workstation/Tools/phantomjs-2.1.1-macosx/bin/phantomjs");

        return new PhantomJSDriver(dcaps);
    }

    public static void main(String[] args) {

        WebDriver driver = getPhantomJSDriver();
        driver.get("https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.767c2420x1wsEJ&id=520928380305&skuId=3561403886285&areaId=330106&is_b=1&user_id=2541315686&cat_id=55916001&rn=6e62dabd737a68f19303c5871327973e");
        System.out.println(driver.getCurrentUrl());
        WebElement webElement = driver.findElement(By.id("page"));
        String html = webElement.getAttribute("outerHTML");
        Document document = Jsoup.parse(html);
        Elements marketPriceTag = document.select("#J_StrPriceModBox > dd > span");
        if (marketPriceTag != null && marketPriceTag.size() > 0) {
            String marketPrice = marketPriceTag.text();
            System.out.println(marketPrice);
        }
//        html.xpath("//[@id=\"J_StrPriceModBox\"]/dd/span/text()").toString();
    }
}