package com.mullekybernetik.ipd.strategies.basic;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;

public class Defector implements Strategy, Player {

    @Override
    public Player instantiate() {
        return this;
    }

    @Override
    public String toString() {
        return "Defector";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Defector; }

    @Override
    public Move getMove() {
        return Move.DEFECT;
    }

    @Override
    public void setOpponentsMove(Move m) {
    }

}
