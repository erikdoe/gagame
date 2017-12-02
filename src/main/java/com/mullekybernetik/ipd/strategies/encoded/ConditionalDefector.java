package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;


public class ConditionalDefector implements Strategy {

    private String stringRepresentation;
    private Condition[] conditions;

    public ConditionalDefector(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
        this.conditions = stringRepresentation.isEmpty() ? new Condition[0] : parseConditionBlocks(stringRepresentation.split("-"));
    }

    private Condition[] parseConditionBlocks(String[] blocks) {
        Condition[] conditions = new Condition[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            conditions[i] = parseBlock(blocks[i]);
        }
        return conditions;
    }

    private Condition parseBlock(String block) {
        int pattern = 0;
        int mask = 0;
        for (int i = 0; i < block.length(); i++) {
            pattern <<= 2;
            mask <<= 2;
            char c = block.charAt(i);
            if (c == '?') {
                mask |= 0x2; // here we are breaking encapsulation; we assume U is in bit 2 and CD are in bit 1
            } else {
                pattern |= Move.fromChar(c).getValue();
                mask |= 0x3;
            }
        }
        return new Condition(pattern, mask);
    }

    @Override
    public Player instantiate() {
        return new PlayerImpl();
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ConditionalDefector)
                && stringRepresentation.equals(((ConditionalDefector)other).stringRepresentation);
    }


    private static class Condition {
        private int pattern;
        private int mask;

        private Condition(int pattern, int mask) {
            this.pattern = pattern;
            this.mask = mask;
        }
    }


    protected class PlayerImpl implements Player {

        private int memory;

        protected PlayerImpl() {
            for (int i = 0; i < 16; i++) {
                memory <<= 2;
                memory |= Move.UNKNOWN.getValue();
            }
        }

        @Override
        public Move getMove() {
            for (Condition c : conditions) {
                if (((memory & c.mask) == c.pattern)) {
                    return Move.DEFECT;
                }
            }
            return Move.COOPERATE;
        }

        @Override
        public void setOpponentsMove(Move m) {
            memory <<= 2;
            memory |= m.getValue();
        }

    }

}
