package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import com.mullekybernetik.gagame.match.Strategy;

public class Defector extends StrategyBase implements Strategy {

    public void newMatch() {
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
