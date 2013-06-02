package ru.ifmo.ctddev.larionov.bach.classifier.comparator;

import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:22
 */
public interface IComparator {
    public List<WeightedPair> createPairs(Iterable<ISite> list);
}
