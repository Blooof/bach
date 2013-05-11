package ru.ifmo.ctddev.larionov.bach.classifier;

import org.apache.log4j.Logger;
import ru.ifmo.ctddev.larionov.bach.comparator.IComparator;
import ru.ifmo.ctddev.larionov.bach.site.ISite;
import ru.ifmo.ctddev.larionov.bach.site.WeightedPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 0:26
 */
public class Classifier implements IClassifier {

    private static final double DEFAULT_THRESHOLD = 0.5;
    private static final Logger logger = Logger.getLogger(Classifier.class);
    private List<IComparator> siteComparators;
    private double[] multipliers;

    public Classifier(List<IComparator> siteComparators, double[] multipliers) {
        checkArguments(siteComparators, multipliers);

        this.siteComparators = siteComparators;
        this.multipliers = multipliers;
    }

    private void checkArguments(List<IComparator> siteComparators, double[] multipliers) {
        if (siteComparators == null || multipliers == null) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (siteComparators.size() != multipliers.length) {
            throw new IllegalArgumentException("Arguments must have equal size");
        }
    }

    @Override
    public List<WeightedPair> classify(Iterable<ISite> list) {

        Map<WeightedPair, Double> weights = new HashMap<>();

        for (int i = 0; i < siteComparators.size(); i++) {
            IComparator comparator = siteComparators.get(i);

            runComparator(list, weights, multipliers[i], comparator);
        }

        List<WeightedPair> result = extractResults(weights);

        logger.debug("Final results from classifier " + result.toString());
        return result;
    }

    private List<WeightedPair> extractResults(Map<WeightedPair, Double> weights) {
        List<WeightedPair> result = new ArrayList<>();
        for (Map.Entry<WeightedPair, Double> entry : weights.entrySet()) {
            if (entry.getValue() > DEFAULT_THRESHOLD) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private void runComparator(Iterable<ISite> list, Map<WeightedPair, Double> weights,
                               double multiplier, IComparator comparator) {
        List<WeightedPair> currentPairs = comparator.createPairs(list);
        logger.debug(String.format("Comparator %s found these pairs: %s",
                comparator.getClass().toString(), currentPairs.toString()));

        for (WeightedPair pair : currentPairs) {
            if (!weights.containsKey(pair)) {
                weights.put(pair, 0d);
            }

            Double currentWeight = weights.get(pair);
            weights.put(pair, currentWeight + multiplier * pair.getWeight());
        }
    }
}
