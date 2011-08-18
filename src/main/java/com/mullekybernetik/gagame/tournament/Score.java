package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;

public class Score implements Comparable<Score> {

    private Strategy strategy;
    private int points;

    public Score(Strategy strategy) {
        this.strategy = strategy;
    }

    public Score(Strategy strategy, int points) {
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

    public int compareTo(Score other) {
        if (other.points != this.points)
            return this.points - other.points;
        // I'm doing this so that TreeSet sees the objects as different. It can also
        // be viewed as a randomiser for ties.
        return this.hashCode() - other.hashCode();
    }

    @Override
    public String toString() {
        return strategy.getClass().getName() + " " + points;
    }


}