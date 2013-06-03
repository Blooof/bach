package ru.ifmo.ctddev.larionov.bach.comparator;

import org.apache.log4j.Logger;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Oleg Larionov
 * Date: 03.06.13
 * Time: 14:22
 */
public class ComparatorService implements IComparatorService {

    private static final double DEFAULT_WEIGHT_THRESHOLD = 0.5;
    private static final Logger logger = Logger.getLogger(ComparatorService.class);
    private List<IComparator> comparators;
    private double[] multipliers;

    public ComparatorService(List<IComparator> comparators, double[] multipliers) {
        this.comparators = comparators;
        this.multipliers = multipliers;

        checkArguments(comparators, multipliers);
    }

    private void checkArguments(List<IComparator> comparators, double[] multipliers) {
        if (comparators == null || multipliers == null) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (comparators.size() != multipliers.length) {
            throw new IllegalArgumentException("Arguments must have equal size");
        }
    }

    @Override
    public List<WeightedPair> compare(List<ISite> sites) {
        Map<WeightedPair, Double> weights = new HashMap<>();

        for (int i = 0; i < comparators.size(); i++) {
            IComparator comparator = comparators.get(i);

            runComparator(sites, weights, multipliers[i], comparator);
        }

        List<WeightedPair> candidates = extractResults(weights);
        logger.debug("Candidates: " + candidates);

        return candidates;
    }

    private void runComparator(List<ISite> list, Map<WeightedPair, Double> weights,
                               double multiplier, IComparator comparator) {
        List<WeightedPair> currentPairs = comparator.createPairs(list);
        logger.debug(String.format("Comparator %s found %d pairs",
                comparator.getClass().toString(), currentPairs.size()));

        for (WeightedPair pair : currentPairs) {
            if (!weights.containsKey(pair)) {
                weights.put(pair, 0d);
            }

            Double currentWeight = weights.get(pair);
            weights.put(pair, currentWeight + multiplier * pair.getWeight());
        }
    }

    private List<WeightedPair> extractResults(Map<WeightedPair, Double> weights) {
        List<WeightedPair> result = new ArrayList<>();
        for (Map.Entry<WeightedPair, Double> entry : weights.entrySet()) {
            if (entry.getValue() > DEFAULT_WEIGHT_THRESHOLD) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
