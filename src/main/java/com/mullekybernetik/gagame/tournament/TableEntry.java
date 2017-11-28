package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.strategies.Strategy;

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

    public int compareTo(TableEntry other) {
        return (other.points - this.points);  // order descending
    }

}
