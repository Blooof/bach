package ru.ifmo.ctddev.larionov.bach.comparator;

import ru.ifmo.ctddev.larionov.bach.site.ISite;
import ru.ifmo.ctddev.larionov.bach.site.WeightedPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.log;

/**
 * User: Oleg Larionov
 * Date: 06.05.13
 * Time: 23:33
 */
public class URLShinglesComparator implements IComparator {

    private static final int DEFAULT_SHINGLES_COUNT = 2;
    private Map<String, Set<String>> data = new HashMap<>();
    private Map<String, Long> hostPageCount = new HashMap<>();

    @Override
    public List<WeightedPair> createPairs(Iterable<ISite> list) {
        getShinglesData(list);

        Map<WeightedPair, Double> similarity = calculateSimilarity();

        return normalizeSimilarity(similarity);
    }

    private List<WeightedPair> normalizeSimilarity(Map<WeightedPair, Double> similarity) {
        List<WeightedPair> result = new ArrayList<>();
        for (Map.Entry<WeightedPair, Double> entry : similarity.entrySet()) {
            WeightedPair pair = entry.getKey();
            double value = entry.getValue();

            long firstPageCount = hostPageCount.get(pair.getFirstHost());
            long secondPageCount = hostPageCount.get(pair.getSecondHost());
            value /= 1 + 0.1 * (log(firstPageCount) + log(secondPageCount));

            pair.setWeight(value);

            result.add(pair);
        }

        return result;
    }

    private Map<WeightedPair, Double> calculateSimilarity() {
        Map<WeightedPair, Double> similarity = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : data.entrySet()) {
            String[] hosts = entry.getValue().toArray(new String[]{});
            if (hosts.length > 1) {
                for (int i = 0; i < hosts.length - 1; i++) {
                    for (int j = i + 1; j < hosts.length; j++) {
                        WeightedPair pair = new WeightedPair(hosts[i], hosts[j]);
                        if (!similarity.containsKey(pair)) {
                            similarity.put(pair, 0d);
                        }

                        double value = similarity.get(pair);
                        similarity.put(pair, value + (1.0 / hosts.length));
                    }
                }
            }
        }
        return similarity;
    }

    private void getShinglesData(Iterable<ISite> list) {
        for (ISite site : list) {
            incrementHostPageCount(site);

            addShingles(site);
        }
    }

    private void addShingles(ISite site) {
        String[] urlParts = getParts(site);
        String[] shingles = getShingles(urlParts, DEFAULT_SHINGLES_COUNT);

        for (String shingle : shingles) {
            if (!data.containsKey(shingle)) {
                data.put(shingle, new HashSet<String>());
            }

            data.get(shingle).add(site.getUrl().getHost());
        }
    }

    private void incrementHostPageCount(ISite site) {
        String host = site.getUrl().getHost();
        if (!hostPageCount.containsKey(host)) {
            hostPageCount.put(host, 0l);
        }
        long value = hostPageCount.get(host);
        hostPageCount.put(host, value + 1);
    }

    private String[] getParts(ISite site) {
        String url = site.getUrl().getHost() + site.getUrl().getPath();
        url = url.replaceAll("[0-9]+", "\\*");

        return url.split("[./]");
    }

    private String[] getShingles(String[] urlParts, int partsInShingle) {
        int shinglesCount = urlParts.length - partsInShingle + 1;
        String[] shingles = new String[shinglesCount];

        for (int i = 0; i < shinglesCount; i++) {
            StringBuffer buffer = new StringBuffer();
            for (int j = 0; j < partsInShingle; j++) {
                buffer.append(urlParts[i + j]);
                buffer.append("_");
            }
            buffer.append(i);

            shingles[i] = buffer.toString();
        }

        return shingles;
    }
}
