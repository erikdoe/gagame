package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class TitForTat extends StrategyBase implements Strategy {

    public Player instantiate() {
        return new TitForTatPlayer();
    }

    @Override
    public String toString() {
        return "TitForTat";
    }


    private static class TitForTatPlayer implements  Player {

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
