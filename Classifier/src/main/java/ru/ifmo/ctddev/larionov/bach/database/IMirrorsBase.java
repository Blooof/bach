package ru.ifmo.ctddev.larionov.bach.database;

import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

/**
 * User: Oleg Larionov
 * Date: 29.05.13
 * Time: 1:38
 */
public interface IMirrorsBase {
    public void addMirrors(WeightedPair pair);

    public double checkMirrors(String first, String second);
}
