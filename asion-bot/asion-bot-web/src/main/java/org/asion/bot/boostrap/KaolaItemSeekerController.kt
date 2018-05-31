package org.asion.bot.boostrap

import edu.uci.ics.crawler4j.crawler.CrawlConfig
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer
import org.asion.bot.crawler.KaolaItemCrawler

object KaolaItemSeekerController {
    @JvmStatic
    fun main(args: Array<String>) {
        val crawlStorageFolder = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data"
        val numberOfCrawlers = 11

        val config = CrawlConfig()
        config.crawlStorageFolder = crawlStorageFolder
        config.maxDepthOfCrawling = 3

        /*
         * Instantiate the controller for this crawl.
         */
        val pageFetcher = PageFetcher(config)
        val robotstxtConfig = RobotstxtConfig()
        val robotstxtServer = RobotstxtServer(robotstxtConfig, pageFetcher)
        val controller = CrawlController(config, pageFetcher, robotstxtServer)

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("https://www.kaola.com/category/1472.html?key=&pageSize=60&pageNo=1&sortfield=2&isStock=false&isSelfProduct=false&isPromote=false&isTaxFree=false&factoryStoreTag=-1&isDesc=true&b=&proIds=&source=false&country=&needBrandDirect=false&isNavigation=0&lowerPrice=-1&upperPrice=-1&backCategory=550&headCategoryId=-1&changeContent=c&#topTab")
        controller.addSeed("https://www.kaola.com/category/1472.html?key=&pageSize=60&pageNo=1&sortfield=2&isStock=false&isSelfProduct=false&isPromote=false&isTaxFree=false&factoryStoreTag=-1&isDesc=true&b=&proIds=&source=false&country=&needBrandDirect=false&isNavigation=0&lowerPrice=-1&upperPrice=-1&backCategory=546&headCategoryId=-1&changeContent=c&#topTab")
        controller.addSeed("https://www.kaola.com/category/1473.html?key=&pageSize=60&pageNo=1&sortfield=2&isStock=false&isSelfProduct=false&isPromote=false&isTaxFree=false&factoryStoreTag=-1&isDesc=true&b=&proIds=&source=false&country=&needBrandDirect=false&isNavigation=0&lowerPrice=-1&upperPrice=-1&backCategory=&headCategoryId=&changeContent=type&#topTab")
//        https://www.kaola.com/category/1473.html?key=&pageSize=60&pageNo=1&sortfield=2&isStock=false&isSelfProduct=false&isPromote=false&isTaxFree=false&factoryStoreTag=-1&isDesc=true&b=&proIds=&source=false&country=&needBrandDirect=false&isNavigation=0&lowerPrice=-1&upperPrice=-1&backCategory=736&headCategoryId=-1&changeContent=crumbs_c&#topTab

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(KaolaItemCrawler::class.java, numberOfCrawlers)
    }
}