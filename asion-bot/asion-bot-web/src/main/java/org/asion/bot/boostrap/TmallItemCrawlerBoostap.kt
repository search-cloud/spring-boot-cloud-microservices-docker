package org.asion.bot.boostrap

import com.alibaba.fastjson.JSON
import org.asion.bot.crawler.TmallItemDetailCrawler
import edu.uci.ics.crawler4j.crawler.CrawlConfig
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer
import org.apache.http.message.BasicHeader
import org.asion.bot.CaptureItem
import org.asion.bot.helper.RedisClientLocal
import kotlin.streams.toList


object TmallItemCrawlerBoostap {

    @JvmStatic
    fun main(args: Array<String>) {

        val crawlStorageFolder = "/Users/Asion/Workstation/Personal/java-workspace/spring-boot-cloud-microservices-docker/data"
        val numberOfCrawlers = 7

        val config = CrawlConfig()
        config.crawlStorageFolder = crawlStorageFolder
        config.maxDepthOfCrawling = 0
        config.connectionTimeout = 3000000
        config.socketTimeout = 10*1000

        val defaultHeaders: Set<BasicHeader> = hashSetOf(
                BasicHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                , BasicHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                , BasicHeader("accept-encoding", "gzip,deflate,br")
                , BasicHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
                , BasicHeader("content-type", "text/html;charset=GBK")
//                , BasicHeader("connection", "keep-alive")
                , BasicHeader("cookie", "hng=CN%7Czh-CN%7CCNY%7C156; tracknick=%5Cu84DD%5Cu8682%5Cu8681_2008; cookie2=10de310f70c82953c7c14a3854d57a03; t=3abda60f7776adf81316cf69599f33b3; _tb_token_=N5poC7fHMWiB; dnk=%E8%93%9D%E8%9A%82%E8%9A%81_2008; uc1=cart_m=0&cookie14=UoTeOoHBGkgACQ%3D%3D&lng=zh_CN&cookie16=WqG3DMC9UpAPBHGz5QBErFxlCA%3D%3D&existShop=false&cookie21=URm48syIYn73&tag=8&cookie15=WqG3DMC9VAQiUQ%3D%3D&pas=0; _med=dw:1280&dh:800&pw:2560&ph:1600&ist:0; cna=VFfoD6WRz3wCAX13Z9ENC/7Y; cq=ccp%3D1; UM_distinctid=16323ce095597-0863e1dbea17ea-33637f06-fa000-16323ce095668c; enc=cxE86C6Y13UzhvYR0ZfRChCNkZwSdhYndcpPEjVlrUDK7Gw73Dm7mAATeNs3VMChRGrlM7IQKNCmZPtuC%2FDBww%3D%3D; res=scroll%3A1280*5760-client%3A1280*703-offset%3A1280*5760-screen%3A1280*800; isg=BOHh3EPsH1uwYLMt7rpybX3_8Ks7JlXn874K20O23ehHqgF8i95lUA_ICN4see24; pnm_cku822=098%23E1hv4pvUvbpvUvCkvvvvvjiPPF5WzjYVPsz9zj3mPmPOsjtEP2cyAjnmPschtjiWvphvC9vhvvCvpvyCvhQvT1ZvC0Erlj7Q%2Bul1bPoxfwmKHkx%2FQjc6D40OjLVxfwBl5dUf8z7QD76XdegJlw03Ib8rwoAy%2BExrV4tKjrcnI4mODVQEfw3lK2kTKphv8vvvvUrvpvvvvvmmvhCvmPgvvUUvphvW9vvv99CvpvQyvvmmvhCv2moEvpvVmvvC9cXvuphvmvvv9bH5mZ082QhvCvvvMMGtvpvhvvvvv8wCvvpvvUmm")
        )
        config.setDefaultHeaders(defaultHeaders)

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
        val seekItemList = mutableListOf<CaptureItem>()
//        for (i in 1..42) {
//            val itemString = RedisClientLocal.get("itemList$i")
//            val tempList = JSON.parseArray(itemString, SeekItem::class.java)
//            seekItemList.addAll(tempList)
//        }
        val itemString = RedisClientLocal.get("itemList30")
        val tempList = JSON.parseArray(itemString, CaptureItem::class.java)
        seekItemList.add(tempList.get(0))

        seekItemList.parallelStream().forEach {
            RedisClientLocal.set(it.detailLink!!, it, 1)
        }
        val seedUrls = seekItemList.parallelStream().map { it.detailLink }.toList()
        seedUrls.parallelStream().forEach { controller.addSeed(it) }
//        controller.addSeed("https://detail.tmall.hk/item.htm?spm=a220m.1000858.0.0.767c2420x1wsEJ&id=520928380305&skuId=3561403886285&areaId=330106&is_b=1&user_id=2541315686&cat_id=55916001&rn=6e62dabd737a68f19303c5871327973e")

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(TmallItemDetailCrawler::class.java, numberOfCrawlers)
    }
}