package ru.ifmo.ctddev.larionov.bach.classifier;

import client.IComparatorClient;
import org.apache.log4j.Logger;
import ru.ifmo.ctddev.larionov.bach.classifier.checker.IPageChecker;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.database.client.IMirrorsBaseClient;

import java.util.Iterator;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 0:26
 */
public class Classifier implements IClassifier {

    private static final double MIRRORS_BASE_THRESHOLD = 0.4;
    private static final double ZERO = 0.01;
    private static final Logger logger = Logger.getLogger(Classifier.class);
    private IComparatorClient comparatorClient;
    private IPageChecker pageChecker;
    private IMirrorsBaseClient mirrorsBaseClient;

    public Classifier(IComparatorClient comparatorClient, IPageChecker pageChecker,
                      IMirrorsBaseClient mirrorsBaseClient) {

        this.comparatorClient = comparatorClient;
        this.pageChecker = pageChecker;
        this.mirrorsBaseClient = mirrorsBaseClient;
    }

    public Classifier(IComparatorClient comparatorClient, IPageChecker pageChecker) {
        this(comparatorClient, pageChecker, null);
    }

    @Override
    public List<WeightedPair> classify(List<ISite> list) {
        logger.debug("Classify request. ISite count: " + list.size());

        List<WeightedPair> candidates = comparatorClient.compare(list);
        logger.debug("Candidates count: " + candidates.size());

        Iterator<WeightedPair> iterator = candidates.iterator();
        while (iterator.hasNext()) {
            WeightedPair pair = iterator.next();
            double value = pageChecker.checkPair(pair);
            pair.setWeight(value);
            if (value < ZERO) {
                iterator.remove();
            }

            if (mirrorsBaseClient != null && value > MIRRORS_BASE_THRESHOLD) {
                mirrorsBaseClient.addMirrors(pair);
            }
        }

        logger.debug("Final results: " + candidates);
        return candidates;
    }
}
