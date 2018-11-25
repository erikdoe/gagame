package com.doernenburg.ipd.strategies.basic;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import com.doernenburg.ipd.strategies.Strategy;

public class Detective implements Strategy {

    @Override
    public Player instantiate() {
        return new Detective.PlayerImpl();
    }

    @Override
    public String toString() {
        return "Detective";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Detective; }


    private static class PlayerImpl implements Player {

        private int moveCount = 0;
        private boolean opponentDidDefect = false;
        private Move lastMoveOfOpponent;

        @Override
        public Move getMove() {
            switch(moveCount) {
                case 0:
                    return Move.COOPERATE;
                case 1:
                    return Move.DEFECT;
                case 2:
                    return Move.COOPERATE;
                case 3:
                    return Move.COOPERATE;
                default:
                    return !opponentDidDefect ? Move.DEFECT : lastMoveOfOpponent;
            }
        }

        @Override
        public void setOpponentsMove(Move m) {
            lastMoveOfOpponent = m;
            if ((moveCount < 4) && (m == Move.DEFECT)) {
                opponentDidDefect = true;
            }
            moveCount += 1;
        }
    }

}
