package ru.ifmo.ctddev.larionov.bach;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ru.ifmo.ctddev.larionov.bach.crawler.MirrorCrawler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Oleg Larionov
 * Date: 22.05.13
 * Time: 23:28
 */
public class Main {

    private static final String CONFIG_FILENAME = "log4j.properties";

    static {
        PropertyConfigurator.configure(CONFIG_FILENAME);
    }

    /*
    http://php.net http://fi1.php.net/ http://jp1.php.net/
    http://cran.r-project.org/ http://cran.rstudio.com/ http://cran.gis-lab.info/ http://cran.stat.auckland.ac.nz/
    http://arxiv.org/ http://uk.arxiv.org/ http://de.arxiv.org/
    http://www.wikileaks.org/ http://www.wikileaks.no/ http://www.wikileaks.nl/
    http://www.gnupg.org/ http://gnupg.raffsoftware.com/ http://gnupg.parentinginformed.com/
     */
    private static final String[] HOSTS_LIST = new String[]{
            "www.php.net", "fi1.php.net", "jp1.php.net",
            //        "cran.r-project.org", "cran.rstudio.com", "cran.gis-lab.info",
            //        "arxiv.org", "uk.arxiv.org",
            //        "www.wikileaks.org", "www.wikileaks.no",
            //          "www.gnupg.org", "gnupg.raffsoftware.com", "gnupg.parentinginformed.com"
    };
    private static final Set<String> HOSTS = new HashSet<>(Arrays.asList(HOSTS_LIST));
    private static final String FILENAME = "urls.txt";
    private static final String CRAWL_FOLDER = "crawl/";
    private static final int REQUEST_DELAY = 1000;
    private static final int NUMBER_OF_CRAWLERS = 4;
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter(FILENAME)) {
            Settings.getInstance().setHosts(HOSTS);
            Settings.getInstance().setWriter(pw);
            runCrawl();
        } catch (FileNotFoundException e) {
            logger.error("Cannot find file " + FILENAME, e);
        }
    }

    private static void runCrawl() {
        CrawlController controller = createDefaultCrawlController();
        for (String host : HOSTS_LIST) {
            controller.addSeed(String.format("http://%s/", host));
        }
        controller.start(MirrorCrawler.class, NUMBER_OF_CRAWLERS);
    }

    private static CrawlController createDefaultCrawlController() {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_FOLDER);
        config.setPolitenessDelay(REQUEST_DELAY);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create Crawl Controller", e);
        }
    }
}


