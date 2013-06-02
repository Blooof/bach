package ru.ifmo.ctddev.larionov.bach.classifier.checker.linkstrategy;

import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.net.URL;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 16:46
 */
public interface ILinkStrategy {
    public List<Pair<URL, URL>> createLinks(WeightedPair pair, int count);
}
