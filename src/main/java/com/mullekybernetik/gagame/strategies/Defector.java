package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class Defector extends StrategyBase implements Strategy, Player {

    public Player instantiate() {
        return this;
    }

    public Move getMove() {
        return Move.DEFECT;
    }

    public void setOpponentsMove(Move m) {
    }

    @Override
    public String toString() {
        return "Defector";
    }

}
