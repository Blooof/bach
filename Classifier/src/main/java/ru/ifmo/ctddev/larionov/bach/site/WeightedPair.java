package ru.ifmo.ctddev.larionov.bach.site;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 23:30
 */
public class WeightedPair {

    private ISite firstHost;
    private List<ISite> firstHostPages;
    private ISite secondHost;
    private List<ISite> secondHostPages;
    private double weight;

    public WeightedPair(ISite firstHost, List<ISite> firstHostPages,
                        ISite secondHost, List<ISite> secondHostPages,
                        double weight) {
        this.firstHost = firstHost;
        this.firstHostPages = firstHostPages;
        this.secondHost = secondHost;
        this.secondHostPages = secondHostPages;
        this.weight = weight;
    }

    public ISite getFirstHost() {
        return firstHost;
    }

    public List<ISite> getFirstHostPages() {
        return firstHostPages;
    }

    public ISite getSecondHost() {
        return secondHost;
    }

    public List<ISite> getSecondHostPages() {
        return secondHostPages;
    }

    public double getWeight() {
        return weight;
    }
}
