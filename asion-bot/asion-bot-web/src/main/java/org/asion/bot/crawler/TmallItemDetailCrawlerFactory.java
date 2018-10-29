package org.asion.bot.crawler;

import org.asion.bot.parser.DetailParser;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.asion.bot.CaptureItemManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class TmallItemDetailCrawlerFactory implements CrawlController.WebCrawlerFactory<WebCrawler> {

    private final CaptureItemManger captureItemManger;
    private final DetailParser parser;

    @Autowired
    public TmallItemDetailCrawlerFactory(CaptureItemManger captureItemManger, DetailParser parser) {
        this.captureItemManger = captureItemManger;
        this.parser = parser;
    }

    public WebCrawler newInstance() {
        return new TmallItemDetailCrawler(captureItemManger, parser);
    }
}
