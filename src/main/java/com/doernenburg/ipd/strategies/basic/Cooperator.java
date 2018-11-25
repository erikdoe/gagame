package com.doernenburg.ipd.strategies.basic;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import com.doernenburg.ipd.strategies.Strategy;

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
