package ru.ifmo.ctddev.larionov.bach.comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.database.IMirrorsBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 29.05.13
 * Time: 23:55
 */
@Service("mirrorsBaseComparator")
public class MirrorsBaseComparator implements IComparator {

    private IMirrorsBase mirrorsBase;

    @Autowired
    public void setMirrorsBase(IMirrorsBase mirrorsBase) {
        this.mirrorsBase = mirrorsBase;
    }

    @Override
    public List<WeightedPair> createPairs(Iterable<ISite> collection) {
        List<ISite> list = new ArrayList<>();
        for (ISite site : collection) {
            list.add(site);
        }

        List<WeightedPair> result = new ArrayList<>();
        int length = list.size();
        for (int i = 0; i < length - 1; i++) {
            ISite first = list.get(i);
            for (int j = i + 1; j < length; j++) {
                ISite second = list.get(j);

                double weight = mirrorsBase.checkMirrors(first.getHost(), second.getHost());
                if (weight > 0) {
                    result.add(new WeightedPair(first, second, weight));
                }
            }
        }

        return result;
    }
}
