package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;

public class TableEntry implements Comparable<TableEntry> {

    private Strategy strategy;
    private int points;

    public TableEntry(Strategy strategy) {
        this.strategy = strategy;
    }

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

    public void addToPoints(int somePoints) {
            points += somePoints;
        }

    public int compareTo(TableEntry other) {
        if (other.points != this.points)
            return this.points - other.points;
        return this.strategy.hashCode() - other.strategy.hashCode();
    }

    @Override
    public String toString() {
        return strategy.getClass().getName() + " " + points;
    }

}
