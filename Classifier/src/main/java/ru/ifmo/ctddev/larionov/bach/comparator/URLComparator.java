package ru.ifmo.ctddev.larionov.bach.comparator;

import ru.ifmo.ctddev.larionov.bach.site.ISite;

import java.util.StringTokenizer;

import static java.lang.Math.max;

/**
 * User: Oleg Larionov
 * Date: 06.05.13
 * Time: 23:33
 */
public class URLComparator implements IComparator {

    @Override
    public double compare(ISite first, ISite second) {
        String firstHost = getEffectiveHost(first.getHost());
        String secondHost = getEffectiveHost(second.getHost());

        StringTokenizer firstTokenizer = new StringTokenizer(firstHost, "/");
        StringTokenizer secondTokenizer = new StringTokenizer(secondHost, "/");

        int firstTokensCount = firstTokenizer.countTokens();
        int secondTokensCount = secondTokenizer.countTokens();
        int similarTokensCount = 0;
        while (firstTokenizer.hasMoreTokens() && secondTokenizer.hasMoreTokens()) {
            String firstCurrentToken = firstTokenizer.nextToken();
            String secondCurrentToken = secondTokenizer.nextToken();
            if (firstCurrentToken.equals(secondCurrentToken)) {
                ++similarTokensCount;
            }
        }

        return ((double) similarTokensCount) / max(firstTokensCount, secondTokensCount);
    }

    private String getEffectiveHost(String host) {
        int index = host.indexOf("://");
        index = index >= 0 ? index + "://".length() : 0;
        return host.substring(index);
    }
}
