package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

import java.util.Random;

public class RandomChoice extends StrategyBase implements Strategy, Player {

    static private Random generator = new Random();

    public Player instantiate() {
        return this;
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
