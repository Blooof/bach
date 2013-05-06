package ru.ifmo.ctddev.larionov.bach.classifier;

import ru.ifmo.ctddev.larionov.bach.comparator.IComparator;
import ru.ifmo.ctddev.larionov.bach.site.Site;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 0:26
 */
public class Classifier implements IClassifier {

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
    public double classify(Site first, Site second) {
        double result = 0;
        int i = 0;
        for (IComparator comparator : siteComparators) {
            result += multipliers[i++] * comparator.compare(first, second);
        }

        return result;
    }
}
