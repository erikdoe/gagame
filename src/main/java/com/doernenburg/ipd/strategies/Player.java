package com.doernenburg.ipd.strategies;

import com.doernenburg.ipd.match.Move;

public interface Player {

    Move getMove();
    void setOpponentsMove(Move m);

}
