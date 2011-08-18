package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import com.mullekybernetik.gagame.match.Strategy;

import java.util.Random;

public class RandomChoice extends StrategyBase implements Strategy {

    static private Random generator = new Random();

    public void newMatch() {
    }

    public Move getMove() {
        return (generator.nextBoolean() ? Move.COOPERATE : Move.DEFECT);
    }

    public void setOpponentsMove(Move m) {
    }

    @Override
    public String toString() {
        return "RandomChoice";
    }

}
