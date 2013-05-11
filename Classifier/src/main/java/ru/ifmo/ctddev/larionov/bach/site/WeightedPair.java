package ru.ifmo.ctddev.larionov.bach.site;

import java.util.Objects;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 23:30
 */
public class WeightedPair {

    private String firstHost;
    private String secondHost;
    private double weight;

    public WeightedPair(String firstHost, String secondHost,
                        double weight) {
        this.firstHost = firstHost;
        this.secondHost = secondHost;
        this.weight = weight;
    }

    public WeightedPair(String firstHost, String secondHost) {
        this(firstHost, secondHost, 0);
    }

    public String getFirstHost() {
        return firstHost;
    }

    public String getSecondHost() {
        return secondHost;
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
