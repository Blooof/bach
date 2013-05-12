package ru.ifmo.ctddev.larionov.bach.checker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.ifmo.ctddev.larionov.bach.checker.linkstrategy.ILinkStrategy;
import ru.ifmo.ctddev.larionov.bach.checker.textchecker.ITextChecker;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 20:36
 */
@RunWith(MockitoJUnitRunner.class)
public class PageCheckerTest {
    @Mock
    private ILinkStrategy linkStrategy;
    @Mock
    private ITextChecker textChecker;
    @InjectMocks
    private PageChecker pageChecker;
    private WeightedPair weightedPair;

    @Before
    public void setUp() throws Exception {
        weightedPair = createPair();
        Pair<URL, URL> pair = new Pair<>(new URL("http://www.yandex.ru"),
                new URL("http://www.google.com"));
        List<Pair<URL, URL>> linkPairs = Collections.singletonList(pair);
        when(linkStrategy.createLinks(eq(weightedPair), any(Integer.class))).thenReturn(linkPairs);
        when(textChecker.checkText(any(String.class), any(String.class))).thenReturn(1d);
    }

    @Test
    public void testCheckPair() throws Exception {
        double actual = pageChecker.checkPair(weightedPair);

        assertEquals(1d, actual, 1e-6);
    }

    private WeightedPair createPair() throws MalformedURLException {
        ISite firstSite = mockSite("www.google.com");
        ISite secondSite = mockSite("www.yandex.ru");

        return new WeightedPair(firstSite, secondSite);
    }

    private ISite mockSite(String host) {
        ISite site = mock(ISite.class);
        when(site.getHost()).thenReturn(host);
        return site;
    }
}
