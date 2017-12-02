package com.mullekybernetik.ipd.strategies.basic;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;

public class TitForTat implements Strategy {

    public Player instantiate() {
        return new TitForTatPlayer();
    }

    @Override
    public String toString() {
        return "TitForTat";
    }

    @Override
    public boolean equals(Object other) { return other instanceof TitForTat; }


    private static class TitForTatPlayer implements Player {

        private Move lastMoveOfOpponent;

        public Move getMove() {
            if (lastMoveOfOpponent == null)
                return Move.COOPERATE;
            return lastMoveOfOpponent;
        }

        public void setOpponentsMove(Move m) {
            lastMoveOfOpponent = m;
        }
    }

}
