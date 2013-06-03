package client;

import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 03.06.13
 * Time: 14:39
 */
public interface IComparatorClient {
    public List<WeightedPair> compare(List<ISite> sites);
}
