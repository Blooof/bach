package ru.ifmo.ctddev.larionov.bach.comparator;

import ru.ifmo.ctddev.larionov.bach.site.Site;

import java.util.StringTokenizer;

import static java.lang.Math.max;

/**
 * User: Oleg Larionov
 * Date: 06.05.13
 * Time: 23:33
 */
public class URLComparator implements IComparator {

    @Override
    public double compare(Site first, Site second) {
        StringTokenizer firstTokenizer = new StringTokenizer(first.getUrl(), "/");
        StringTokenizer secondTokenizer = new StringTokenizer(second.getUrl(), "/");

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
}
