package com.mullekybernetik.ipd.strategies.basic;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;

public class Cooperator implements Strategy, Player {

    public Player instantiate() {
        return this;
    }

    public Move getMove() {
        return Move.COOPERATE;
    }

    public void setOpponentsMove(Move m) {
    }

    @Override
    public String toString() {
        return "Cooperator";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Cooperator; }
}
