package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;

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
