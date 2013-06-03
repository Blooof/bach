package ru.ifmo.ctddev.larionov.bach.comparator;

import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 03.06.13
 * Time: 14:22
 */
public interface IComparatorService {
    public List<WeightedPair> compare(List<ISite> sites);
}
