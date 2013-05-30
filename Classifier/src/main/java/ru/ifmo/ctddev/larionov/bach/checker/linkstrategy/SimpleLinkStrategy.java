package ru.ifmo.ctddev.larionov.bach.checker.linkstrategy;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 16:55
 */
@Service("linkStrategy")
public class SimpleLinkStrategy implements ILinkStrategy {

    private static final Logger logger = Logger.getLogger(SimpleLinkStrategy.class);

    @Override
    public List<Pair<URL, URL>> createLinks(WeightedPair pair, int count) {
        List<Pair<URL, URL>> result = new ArrayList<>();

        ISite first = pair.getFirstHost();
        ISite second = pair.getSecondHost();

        int fromFirst = count / 2;
        int fromSecond = count - fromFirst;

        Random rnd = new Random();
        createLinks(first.getLinks(), result, second.getHost(), rnd, fromFirst);
        createLinks(second.getLinks(), result, first.getHost(), rnd, fromSecond);

        return result;
    }

    private void createLinks(List<URL> from, List<Pair<URL, URL>> to, String hostName, Random rnd, int count) {
        for (int i = 0; i < count; i++) {
            URL firstUrl = from.get(rnd.nextInt(from.size()));
            String path = firstUrl.getPath();
            if (firstUrl.getQuery() != null) {
                path += "?" + firstUrl.getQuery();
            }

            try {
                URL secondUrl = new URL(String.format("%s://%s%s", firstUrl.getProtocol(), hostName, path));
                to.add(new Pair<>(firstUrl, secondUrl));
            } catch (MalformedURLException e) {
                logger.warn("Cannot create url", e);
            }
        }
    }
}
