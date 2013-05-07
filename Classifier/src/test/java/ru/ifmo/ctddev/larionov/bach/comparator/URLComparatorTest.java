package ru.ifmo.ctddev.larionov.bach.comparator;

import org.junit.Test;
import ru.ifmo.ctddev.larionov.bach.site.Site;

import static org.junit.Assert.assertEquals;

/**
 * User: Oleg Larionov
 * Date: 06.05.13
 * Time: 23:42
 */
public class URLComparatorTest {

    private static final double EPS = 1e-6;
    private static final URLComparator urlComparator = new URLComparator();

    @Test
    public void testCompareIdenticalURLs() {
        String url = "http://some.site/with/long/url";
        Site first = getSiteWithUrl(url);
        Site second = getSiteWithUrl(url);

        double actual = urlComparator.compare(first, second);

        assertEquals(1.0f, actual, EPS);
    }

    @Test
    public void testCompareFullyDifferentURLs() {
        Site first = getSiteWithUrl("https://fully/different/urls");
        Site second = getSiteWithUrl("http://not/a/single/match");

        double actual = urlComparator.compare(first, second);

        assertEquals(0.0f, actual, EPS);
    }

    @Test
    public void testFiftyPercentSimilar() {
        Site first = getSiteWithUrl("http://exactly/fifty/percent/similar/urls/ru");
        Site second = getSiteWithUrl("http://exactly/50/percent/similar/sites/com");

        double actual = urlComparator.compare(first, second);

        assertEquals(0.5f, actual, EPS);
    }

    private Site getSiteWithUrl(String url) {
        return new Site(url);
    }
}
