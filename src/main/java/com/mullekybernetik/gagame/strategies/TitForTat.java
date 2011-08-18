package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import com.mullekybernetik.gagame.match.Strategy;

public class TitForTat extends StrategyBase implements Strategy {

    private Move lastMoveOfOpponent;

    public void newMatch() {
        lastMoveOfOpponent = null;
    }

    public Move getMove() {
        if (lastMoveOfOpponent == null)
            return Move.COOPERATE;
        return lastMoveOfOpponent;
    }

    public void setOpponentsMove(Move m) {
        lastMoveOfOpponent = m;
    }

    @Override
    public String toString() {
        return "TitForTat";
    }

}
