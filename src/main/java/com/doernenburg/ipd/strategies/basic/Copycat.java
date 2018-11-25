package com.doernenburg.ipd.strategies.basic;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import com.doernenburg.ipd.strategies.Strategy;

public class Copycat implements Strategy {

    @Override
    public Player instantiate() {
        return new PlayerImpl();
    }

    @Override
    public String toString() {
        return "Copycat";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Copycat; }


    private static class PlayerImpl implements Player {

        private Move lastMoveOfOpponent;

        @Override
        public Move getMove() {
            if (lastMoveOfOpponent == null)
                return Move.COOPERATE;
            return lastMoveOfOpponent;
        }

        @Override
        public void setOpponentsMove(Move m) {
            lastMoveOfOpponent = m;
        }
    }

}
