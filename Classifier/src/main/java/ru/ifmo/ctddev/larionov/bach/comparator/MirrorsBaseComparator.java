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

        return mirrorsBase.checkMirrors(list);
    }
}
