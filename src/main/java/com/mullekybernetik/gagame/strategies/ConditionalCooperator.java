package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class ConditionalCooperator extends ConditionalDefector {

    public ConditionalCooperator(String stringRepresentation) {
        super(stringRepresentation);
    }

    @Override
    public Player instantiate() {
        return new PlayerImpl();
    }

    private class PlayerImpl extends ConditionalDefector.PlayerImpl {

        @Override
        public Move getMove() {
            return (super.getMove() == Move.COOPERATE) ? Move.DEFECT : Move.COOPERATE;
        }

    }

}
