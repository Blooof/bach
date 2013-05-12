package ru.ifmo.ctddev.larionov.bach.checker;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.ctddev.larionov.bach.checker.linkstrategy.ILinkStrategy;
import ru.ifmo.ctddev.larionov.bach.checker.textchecker.ITextChecker;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 16:43
 */
public class PageChecker implements IPageChecker {

    private static final Logger logger = Logger.getLogger(PageChecker.class);
    private static final int TIMEOUT = 3000;
    private static final int DEFAULT_LINKS_COUNT = 20;
    @Autowired
    private ILinkStrategy linkStrategy;
    @Autowired
    private ITextChecker textChecker;

    public PageChecker() {
    }

    public void setLinkStrategy(ILinkStrategy linkStrategy) {
        this.linkStrategy = linkStrategy;
    }

    public void setTextChecker(ITextChecker textChecker) {
        this.textChecker = textChecker;
    }

    @Override
    public double checkPair(WeightedPair pair) {
        List<Pair<URL, URL>> linksList = linkStrategy.createLinks(pair, DEFAULT_LINKS_COUNT);

        double result = 0;
        int validPairs = 0;
        for (Pair<URL, URL> links : linksList) {
            try {
                String text1 = getText(links.getFirst());
                String text2 = getText(links.getSecond());
                result += textChecker.checkText(text1, text2);
                validPairs++;
            } catch (IOException e) {
                logger.warn("Cannot get page", e);
            }
        }

        if (validPairs > DEFAULT_LINKS_COUNT / 2) {
            result = 0;
        } else {
            result /= validPairs;
        }

        logger.debug("Resemblance value for " + pair + " = " + result);
        return result;
    }

    private String getText(URL url) throws IOException {
        return Jsoup.parse(url, TIMEOUT).body().text();
    }
}
