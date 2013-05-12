package ru.ifmo.ctddev.larionov.bach.checker.linkstrategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 17:15
 */
public class SimpleLinkStrategyTest {

    private SimpleLinkStrategy strategy;
    private WeightedPair pair;

    @Before
    public void setUp() throws Exception {
        strategy = new SimpleLinkStrategy();

        List<URL> first = Arrays.asList(new URL("http://www.google.com/calendar"), new URL("http://www.google.com/mail"));
        List<URL> second = Arrays.asList(new URL("http://www.yandex.ru/check"), new URL("http://www.yandex.ru/market"),
                new URL("http://www.yandex.ru/phones"));

        ISite firstSite = mockSite("www.google.com", first);
        ISite secondSite = mockSite("www.yandex.ru", second);

        pair = new WeightedPair(firstSite, secondSite);
    }

    private ISite mockSite(String host, List<URL> urls) {
        ISite site = mock(ISite.class);
        when(site.getHost()).thenReturn(host);
        when(site.getLinks()).thenReturn(urls);
        return site;
    }

    @Test
    public void testCreateLinks() throws Exception {
        int linksCount = 2;

        List<Pair<URL, URL>> pairs = strategy.createLinks(pair, linksCount);

        Assert.assertEquals(linksCount, pairs.size());
    }
}
