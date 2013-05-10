package ru.ifmo.ctddev.larionov.bach.site;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 23:30
 */
public class WeightedPair {

    private List<ISite> firstHostPages;
    private List<ISite> secondHostPages;
    private double weight;

    public WeightedPair(List<ISite> firstHostPages,
                        List<ISite> secondHostPages,
                        double weight) {
        this.firstHostPages = firstHostPages;
        this.secondHostPages = secondHostPages;
        this.weight = weight;
    }

    public List<ISite> getFirstHostPages() {
        return firstHostPages;
    }

    public List<ISite> getSecondHostPages() {
        return secondHostPages;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "WeightedPair{" +
                "firstHostPages=" + firstHostPages +
                ", secondHostPages=" + secondHostPages +
                ", weight=" + weight +
                '}';
    }
}
