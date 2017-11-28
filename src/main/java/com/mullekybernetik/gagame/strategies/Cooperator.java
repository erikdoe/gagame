package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

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

}
