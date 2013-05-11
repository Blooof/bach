package ru.ifmo.ctddev.larionov.bach.site;

import java.util.List;
import java.util.Objects;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 23:30
 */
public class WeightedPair {

    private String firstHost;
    private String secondHost;
    private List<ISite> firstHostPages;
    private List<ISite> secondHostPages;
    private double weight;

    public WeightedPair(String firstHost, String secondHost,
                        List<ISite> firstHostPages, List<ISite> secondHostPages,
                        double weight) {
        this.firstHost = firstHost;
        this.secondHost = secondHost;
        this.firstHostPages = firstHostPages;
        this.secondHostPages = secondHostPages;
        this.weight = weight;
    }

    public String getFirstHost() {
        return firstHost;
    }

    public String getSecondHost() {
        return secondHost;
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
                "firstHost=" + firstHost +
                ", secondHost=" + secondHost +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstHost, secondHost, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final WeightedPair other = (WeightedPair) obj;
        return Objects.equals(this.firstHost, other.firstHost)
                && Objects.equals(this.secondHost, other.secondHost)
                && Objects.equals(this.weight, other.weight);
    }
}
