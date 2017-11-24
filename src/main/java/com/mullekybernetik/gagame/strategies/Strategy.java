package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public interface Strategy {

    void newMatch();

    Move getMove();

    void setOpponentsMove(Move m);

    Strategy clone();

}
