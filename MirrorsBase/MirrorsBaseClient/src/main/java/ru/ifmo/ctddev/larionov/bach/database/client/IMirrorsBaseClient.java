package ru.ifmo.ctddev.larionov.bach.database.client;

import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 02.06.13
 * Time: 15:01
 */
public interface IMirrorsBaseClient {
    public boolean addMirrors(WeightedPair pair);

    public double checkMirrors(Pair<String, String> hosts);

    public List<WeightedPair> checkMirrors(List<ISite> sites);
}
