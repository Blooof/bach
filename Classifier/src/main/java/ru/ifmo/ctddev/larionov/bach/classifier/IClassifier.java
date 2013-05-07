package ru.ifmo.ctddev.larionov.bach.classifier;

import ru.ifmo.ctddev.larionov.bach.site.ISite;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 0:21
 */
public interface IClassifier {

    public double classify(ISite first, ISite second);
}
