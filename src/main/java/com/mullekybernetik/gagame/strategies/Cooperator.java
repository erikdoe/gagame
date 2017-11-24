package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class Cooperator extends StrategyBase implements Strategy {

    public void newMatch() {
    }

    public Move getMove() {
        return Move.COOPERATE;
    }

    public void setOpponentsMove(Move m) {
    }

    public Strategy clone() {
        return this;
    }

    @Override
    public String toString() {
        return "Cooperator";
    }

}
