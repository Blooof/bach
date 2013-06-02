package ru.ifmo.ctddev.larionov.bach.classifier.comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.database.client.IMirrorsBaseClient;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 29.05.13
 * Time: 23:55
 */
@Service("mirrorsBaseComparator")
public class MirrorsBaseComparator implements IComparator {

    private IMirrorsBaseClient mirrorsBaseClient;

    @Autowired
    public void setMirrorsBase(IMirrorsBaseClient mirrorsBaseClient) {
        this.mirrorsBaseClient = mirrorsBaseClient;
    }

    @Override
    public List<WeightedPair> createPairs(Iterable<ISite> collection) {
        List<ISite> list = new ArrayList<>();
        for (ISite site : collection) {
            list.add(site);
        }

        return mirrorsBaseClient.checkMirrors(list);
    }
}
