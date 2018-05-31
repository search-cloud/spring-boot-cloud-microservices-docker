package org.asion.bot.crawler

import org.asion.bot.parser.Parser
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.crawler.WebCrawler
import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItemManger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class TmallItemListCrawlerFactory @Autowired
constructor(private val captureItemManger: CaptureItemManger,
            @param:Qualifier("itemListParser") private val parser: Parser<CaptureItem>)
    : CrawlController.WebCrawlerFactory<WebCrawler> {

    override fun newInstance(): WebCrawler {
        return TmallItemListCrawler(captureItemManger, parser)
    }
}
