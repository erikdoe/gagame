package com.mullekybernetik.gagame.match;

public interface Strategy {

    void newMatch();

    Move getMove();

    void setOpponentsMove(Move m);

}
