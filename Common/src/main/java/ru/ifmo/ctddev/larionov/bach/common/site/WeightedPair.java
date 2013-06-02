package ru.ifmo.ctddev.larionov.bach.common.site;

import java.util.Objects;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 23:30
 */
public class WeightedPair {

    private ISite firstHost;
    private ISite secondHost;
    private double weight;

    public WeightedPair() {
    }

    public WeightedPair(ISite firstHost, ISite secondHost,
                        double weight) {
        this.firstHost = firstHost;
        this.secondHost = secondHost;
        this.weight = weight;
    }

    public WeightedPair(ISite firstHost, ISite secondHost) {
        this(firstHost, secondHost, 0);
    }

    public ISite getFirstHost() {
        return firstHost;
    }

    public void setFirstHost(ISite firstHost) {
        this.firstHost = firstHost;
    }

    public ISite getSecondHost() {
        return secondHost;
    }

    public void setSecondHost(ISite secondHost) {
        this.secondHost = secondHost;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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
        String firstHost = this.firstHost.getHost(), secondHost = this.secondHost.getHost();
        if (firstHost.compareTo(secondHost) > 0) {
            String tmp = firstHost;
            firstHost = secondHost;
            secondHost = tmp;
        }
        return Objects.hash(firstHost + secondHost);
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
        boolean result = Objects.equals(firstHost, other.firstHost)
                && Objects.equals(secondHost, other.secondHost);
        result = result || (Objects.equals(secondHost, other.firstHost)
                && Objects.equals(firstHost, other.secondHost));
        return result;
    }
}
