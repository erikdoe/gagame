package com.mullekybernetik.ipd.strategies.basic;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;

public class Defector implements Strategy, Player {

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

    @Override
    public boolean equals(Object other) { return other instanceof Defector; }

}
