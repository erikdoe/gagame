package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public interface Player {

    Move getMove();

    void setOpponentsMove(Move m);

}
