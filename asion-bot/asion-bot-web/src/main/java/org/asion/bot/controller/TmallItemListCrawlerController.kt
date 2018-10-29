package org.asion.bot.controller

import com.ytx.common.vo.YTXResponse
import org.asion.bot.crawler.TmallItemListCrawlerFactory
import edu.uci.ics.crawler4j.crawler.CrawlConfig
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer
import org.apache.http.message.BasicHeader
import org.asion.bot.helper.RedisClientLocal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/seek/item/list")
class TmallItemListCrawlerController {

    @Autowired
    lateinit var tmallItemListCrawlerFactory: TmallItemListCrawlerFactory

    @GetMapping("/tmall")
    @ResponseBody
    fun seekItem(): YTXResponse {

        val crawlStorageFolder = "/Users/Asion/Workstation/Work/ytx/Workspace/ytx-parent/ytx-seeker/data"
        val numberOfCrawlers = 1

        val config = CrawlConfig()
        config.crawlStorageFolder = crawlStorageFolder
        config.maxDepthOfCrawling = 2
        config.connectionTimeout = 3000000
        config.socketTimeout = 10*1000

//        config.proxyHost = "127.0.0.1"
//        config.proxyPort = 60593
//        config.proxyUsername = "luxuexian99@gmail.com"

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
        robotstxtConfig.isEnabled = false
        val robotstxtServer = RobotstxtServer(robotstxtConfig, pageFetcher)
        val controller = CrawlController(config, pageFetcher, robotstxtServer)

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        val seedUrls = RedisClientLocal.keys("https://list.tmall.com/*", 0)
        seedUrls.parallelStream().forEach { controller.addSeed(it) }

//        controller.addSeed("https://list.tmall.com/search_product.htm?pos=1&cat=50026391&theme=344&acm=201603079.1003.2.1311809&scm=1003.2.201603079.OTHER_1484994380835_1311809&active=1&style=l&sort=d")

//        controller.addSeed("https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.4.e47545edxU7YfH&cat=52792006&q=%B1%A3%CA%AA%B2%B9%CB%AE&sort=d&style=l&auction_tag=71682;13186;&active=1&industryCatId=52792006&tmhkmain=1#J_Filter")
//        controller.addSeed("https://list.tmall.hk/search_product.htm?spm=a220m.1000858.0.0.73f545edKs1nvp&cat=52792006&q=保湿补水&sort=d&style=l&auction_tag=71682;13186;&active=1&industryCatId=52792006&morefilter=0&morefilter=0&tmhkmain=1#J_Filter")
//        controller.addSeed("https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.579a45ed5MRrUl&cat=52792006&s=0&q=保湿补水&sort=d&style=l&auction_tag=71682;13186;&active=1&industryCatId=52792006&morefilter=0&morefilter=0&tmhkmain=1&type=pc#J_Filter")

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.startNonBlocking(tmallItemListCrawlerFactory, numberOfCrawlers)

        return YTXResponse.builder().addData("seedUrl", seedUrls).build()
    }
}

//        val seedUrls = arrayListOf(
//                "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.579a45ed5MRrUl&cat=52792006&s=0&q=保湿补水&sort=d&style=l&auction_tag=71682;13186;&active=1&industryCatId=52792006&morefilter=0&morefilter=0&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.1f4445edxE1Jbg&cat=52792006&s=84&q=保湿补水&sort=d&style=l&auction_tag=71682;13186;&active=1&industryCatId=52792006&morefilter=0&morefilter=0&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.2b7745edeJXpmq&cat=52792006&s=168&q=保湿补水&sort=d&style=l&auction_tag=71682;13186;&active=1&industryCatId=52792006&morefilter=0&morefilter=0&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.4.5dc1f881ZwILVH&cat=55934001&s=0&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.1214f881OyQ2ZT&cat=55934001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.6de3f881RYrO70&cat=55934001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.6.be9a12fezyVlZ9&cat=55938001&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863516774_993&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_14455552444812_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.218b75aditALBm&cat=55938001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.632775adARTxzf&cat=55938001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.7.be9a12fezyVlZ9&cat=55930003&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863528972_1236&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_14455548597333_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.2b771e56FsNiXu&cat=55930003&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.3f9b1e563K3NVp&cat=55930003&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.8.be9a12fezyVlZ9&cat=55940001&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863549068_1481&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_14455529359904_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.551c76cd08ywfY&cat=55940001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.2bb176cd8YdgyJ&cat=55940001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.9.be9a12fezyVlZ9&cat=55930002&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863572727_737&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_14455525512425_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.47bd221fxs0iwN&cat=55930002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.41e4221f6fXeto&cat=55930002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.10.be9a12fezyVlZ9&cat=55918001&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863590314_1105&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_14455537054906_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.1acf98774Yul2n&cat=55918001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.19c39877xDOsru&cat=55918001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.4.921330e8gJV59Y&cat=55916006&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.387f30e88Rx2xp&cat=55916006&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.592530e8ZgXUU5&cat=55916006&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.4.dd15278fBGXmOw&cat=55926001&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.7dc6278fAhwJrm&cat=55926001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.6123278f9vL57K&cat=55926001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.13.be9a12fezyVlZ9&cat=55920001&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863647834_2333&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_14455510122519_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.c1c72f2atGtg6A&cat=55920001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.31c92f2aTHqaql&cat=55920001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.14.be9a12fezyVlZ9&cat=55924001&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863667943_2581&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_144555216649910_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.45137f3a4XhhzS&cat=55924001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.2e0f7f3a1Wo5lb&cat=55924001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.15.be9a12fezyVlZ9&cat=55942003&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863693311_2949&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_144555178175111_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.16.be9a12fezyVlZ9&cat=55926002&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863716508_3412&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_144554985800812_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.30e25cb2kKcJeG&cat=55926002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.756e5cb2t5XxLY&cat=55926002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.17.be9a12fezyVlZ9&cat=55914003&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863731762_3902&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_144554947326013_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.61a458beDZT66N&cat=55914003&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.47e158be9nUxtB&cat=55914003&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.18.be9a12fezyVlZ9&cat=55916002&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863747374_4147&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_144555062750914_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.4e3f1dc8BwRzEA&cat=55916002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.320d1dc8nKZPrB&cat=55916002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.19.be9a12fezyVlZ9&cat=55944001&sort=d&acm=lb-zebra-34359-425774.1003.8.519251&_ksTS=1429863761463_4390&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425774.ITEM_144555024276115_519251"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.65d115aafbokjT&cat=55944001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.570915aaotgDLm&cat=55944001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.32.be9a12fezyVlZ9&cat=55932001&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862255256_585&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455545494340_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.2e561b3bh6WxMU&cat=55932001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.10d61b3bpysuuB&cat=55932001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.33.be9a12fezyVlZ9&cat=55950003&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862287170_954&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455541646861_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.20805fc7CyMr2F&cat=55950003&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.6f155fc766UHhM&cat=55950003&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.34.be9a12fezyVlZ9&cat=55952002&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862312916_1190&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455553189342_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.c50e75f0jsGScz&cat=55952002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.619075f01Mjqfx&cat=55952002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.35.be9a12fezyVlZ9&cat=55948002&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862330970_1430&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455549341863_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.36.be9a12fezyVlZ9&cat=55920005&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862342654_1668&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455530104434_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.3f3e7b1dX5awL1&cat=55920005&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.20367b1doKl2ps&cat=55920005&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.37.be9a12fezyVlZ9&cat=55980001&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862358333_1908&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455526256955_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.5c00482bi8Gx5m&cat=55980001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.b314482bhl2p8i&cat=55980001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.38.be9a12fezyVlZ9&cat=55978001&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862372789_2145&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455537799436_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.1fc53b25huhOFN&cat=55978001&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.5d213b25iihK5m&cat=55978001&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.39.be9a12fezyVlZ9&cat=55922002&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862383261_2385&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455533951957_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.563c1aa6EIfjLD&cat=55922002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.1dd41aa6QTAArX&cat=55922002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.40.be9a12fezyVlZ9&cat=55964002&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862396109_2624&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455514714518_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.110875069xjX8o&cat=55964002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.41.be9a12fezyVlZ9&cat=55916005&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862437238_2863&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_14455510867039_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.48f5739ab2t1o4&cat=55916005&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.3830739asfzX4t&cat=55916005&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.42.be9a12fezyVlZ9&cat=55954002&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862451916_3101&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_144555224095110_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.2e1a4429ju7U8G&cat=55954002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.6c234429Fgbznl&cat=55954002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.43.be9a12fezyVlZ9&cat=55932002&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862467954_3339&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_144555185620311_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.6e9c4b693kOTNg&cat=55932002&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.576e4b69TxtKhP&cat=55932002&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120102.44.be9a12fezyVlZ9&cat=55950004&sort=d&acm=lb-zebra-34359-425776.1003.8.519261&_ksTS=1429862485338_3580&from=sn_1_rightnav&_input_charset=utf-8&style=l&s=0&search_condition=7&industryCatId=52830004&callback=__jsonp_cb&active=1&abtest=null&scm=1003.8.lb-zebra-34359-425776.ITEM_144554993246012_519261"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.10.47047659qo3yA3&cat=55950004&s=84&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//                , "https://list.tmall.hk/search_product.htm?spm=a220m.1000858.1000724.11.20367659uZ8oCv&cat=55950004&s=168&sort=d&style=l&search_condition=7&auction_tag=71682;&from=sn_1_rightnav&active=1&industryCatId=52830004&tmhkmain=1&type=pc#J_Filter"
//
//        )