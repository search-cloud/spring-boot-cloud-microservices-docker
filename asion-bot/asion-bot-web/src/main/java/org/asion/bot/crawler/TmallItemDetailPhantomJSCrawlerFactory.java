package org.asion.bot.crawler;

import org.asion.bot.parser.PhantomJSParser;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.asion.bot.CaptureItem;
import org.asion.bot.CaptureItemManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class TmallItemDetailPhantomJSCrawlerFactory implements CrawlController.WebCrawlerFactory<WebCrawler> {

    private final CaptureItemManger captureItemManger;
    private final PhantomJSParser<CaptureItem> parser;

    @Autowired
    public TmallItemDetailPhantomJSCrawlerFactory(CaptureItemManger captureItemManger, @Qualifier("itemDetailPhantomJSParser") PhantomJSParser<CaptureItem> parser) {
        this.captureItemManger = captureItemManger;
        this.parser = parser;
    }

    public WebCrawler newInstance() {
        return new TmallItemDetailPhantomJSCrawler(captureItemManger, parser);
    }
}
