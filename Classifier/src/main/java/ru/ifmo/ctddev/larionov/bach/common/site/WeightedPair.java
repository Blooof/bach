package ru.ifmo.ctddev.larionov.bach.common.site;

import ru.ifmo.ctddev.larionov.bach.common.Pair;

import java.util.Objects;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 23:30
 */
public class WeightedPair {

    private Pair<ISite, ISite> pair;
    private double weight;

    public WeightedPair(ISite firstHost, ISite secondHost,
                        double weight) {
        pair = new Pair<>(firstHost, secondHost);
        this.weight = weight;
    }

    public WeightedPair(ISite firstHost, ISite secondHost) {
        this(firstHost, secondHost, 0);
    }

    public ISite getFirstHost() {
        return pair.getFirst();
    }

    public ISite getSecondHost() {
        return pair.getSecond();
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
                "pair=" + pair +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair);
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
        return Objects.equals(this.pair, other.pair);
    }
}
