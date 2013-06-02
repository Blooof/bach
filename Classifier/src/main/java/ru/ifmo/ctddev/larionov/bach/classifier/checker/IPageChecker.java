package ru.ifmo.ctddev.larionov.bach.classifier.checker;

import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 16:38
 */
public interface IPageChecker {
    public double checkPair(WeightedPair pair);
}
