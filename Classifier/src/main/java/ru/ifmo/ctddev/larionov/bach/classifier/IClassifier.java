package ru.ifmo.ctddev.larionov.bach.classifier;

import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 0:21
 */
public interface IClassifier {

    public List<WeightedPair> classify(Iterable<ISite> list);
}
