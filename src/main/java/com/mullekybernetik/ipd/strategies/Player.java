package com.mullekybernetik.ipd.strategies;

import com.mullekybernetik.ipd.match.Move;

public interface Player {

    Move getMove();

    void setOpponentsMove(Move m);

}
