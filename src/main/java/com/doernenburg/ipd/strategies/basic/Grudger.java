package com.doernenburg.ipd.strategies.basic;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import com.doernenburg.ipd.strategies.Strategy;

public class Grudger implements Strategy {

    @Override
    public Player instantiate() {
        return new Grudger.PlayerImpl();
    }

    @Override
    public String toString() {
        return "Grudger";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Grudger; }


    private static class PlayerImpl implements Player {

        private boolean opponentDidDefect = false;

        @Override
        public Move getMove() {
            if (opponentDidDefect)
                return Move.DEFECT;
            return Move.COOPERATE;
        }

        @Override
        public void setOpponentsMove(Move m) {
            if (m == Move.DEFECT) {
                opponentDidDefect = true;
            }
        }
    }

}
