package ru.ifmo.ctddev.larionov.bach.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.log4j.Logger;
import ru.ifmo.ctddev.larionov.bach.Settings;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: Oleg Larionov
 * Date: 22.05.13
 * Time: 22:57
 */
public class MirrorCrawler extends WebCrawler {

    private static final Logger logger = Logger.getLogger(MirrorCrawler.class);
    private static final AtomicLong count = new AtomicLong();

    @Override
    public boolean shouldVisit(WebURL url) {
        try {
            String hostName = new URL(url.getURL()).getHost();
            return Settings.getInstance().checkHost(hostName);
        } catch (MalformedURLException e) {
            logger.warn("Cannot form URL from " + url.getURL(), e);
            return false;
        }
    }

    @Override
    public void visit(Page page) {
        if (page.getContentType().contains("text/html")) {
            PrintWriter writer = Settings.getInstance().getWriter();
            String url = page.getWebURL().getURL();
            writer.println(url);
            logger.info("Visit " + url);
            long currentCount = count.incrementAndGet();
            if (currentCount % 100 == 0) {
                writer.flush();
                logger.info("Flush file");
            }
        }
    }
}
