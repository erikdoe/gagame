package com.mullekybernetik.ipd.strategies.basic;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;

public class Cooperator implements Strategy, Player {

    @Override
    public Player instantiate() {
        return this;
    }

    @Override
    public String toString() {
        return "Cooperator";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Cooperator; }

    @Override
    public Move getMove() {
        return Move.COOPERATE;
    }

    @Override
    public void setOpponentsMove(Move m) {
    }

}
