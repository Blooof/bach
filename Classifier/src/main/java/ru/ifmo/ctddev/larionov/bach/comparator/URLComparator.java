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
        StringTokenizer firstTokenizer = new StringTokenizer(first.getHost(), "/");
        StringTokenizer secondTokenizer = new StringTokenizer(second.getHost(), "/");

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
