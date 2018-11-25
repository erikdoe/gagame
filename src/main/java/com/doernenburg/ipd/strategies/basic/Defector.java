package com.doernenburg.ipd.strategies.basic;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import com.doernenburg.ipd.strategies.Strategy;

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
