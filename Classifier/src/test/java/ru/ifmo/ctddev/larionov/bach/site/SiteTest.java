package ru.ifmo.ctddev.larionov.bach.site;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 23:16
 */
public class SiteTest {

    @Test
    public void testGetHost() {
        String testUrl = "http://test.url";
        Site site = new Site(testUrl);

        assertEquals(testUrl, site.getHost());
    }
}
