package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.Strategy;

public class TableEntry implements Comparable<TableEntry> {

    private final Strategy strategy;
    private final int points;

    public TableEntry(Strategy strategy, int points) {
        this.strategy = strategy;
        this.points = points;
    }

    public Strategy getStrategy() {
            return strategy;
        }

    public int getPoints() {
            return points;
        }

    @Override
    public int compareTo(TableEntry other) {
        return (other.points - this.points);  // order descending
    }

}
