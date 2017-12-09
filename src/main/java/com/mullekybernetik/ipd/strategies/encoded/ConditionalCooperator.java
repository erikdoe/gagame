package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.Strategy;


public class ConditionalCooperator implements Strategy {

    private final String stringRepresentation;
    private final Condition[] conditions;

    public ConditionalCooperator(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
        this.conditions = parseConditionBlocks(stringRepresentation.split("-"));
    }

    private static Condition[] parseConditionBlocks(String[] blocks) {
        Condition[] conditions = new Condition[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            conditions[i] = parseBlock(blocks[i]);
        }
        return conditions;
    }

    private static Condition parseBlock(String block) {
        int pattern = 0;
        int mask = 0;
        for (int i = 0; i < block.length(); i++) {
            pattern <<= 2;
            mask <<= 2;
            switch (block.charAt(i)) {
                case '#':                              break;
                case '?': mask |= 0x2;                 break;
                case 'N': mask |= 0x3; pattern |= 0x2; break;
                case 'C': mask |= 0x3; pattern |= 0x1; break;
                case 'D': mask |= 0x3;                 break;
            }
        }
        return new Condition(pattern, mask);
    }

    public String getStringRepresentation() {
        return stringRepresentation;
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
        return (other instanceof ConditionalCooperator)
                && stringRepresentation.equals(((ConditionalCooperator)other).stringRepresentation);
    }


    private static final class Condition {
        private int pattern;
        private int mask;

        private Condition(int pattern, int mask) {
            this.pattern = pattern;
            this.mask = mask;
        }
    }


    protected class PlayerImpl implements Player {

        private int memory = 0xAAAAA; // 0xA = 1010, and 10 is the pattern for move not available

        @Override
        public Move getMove() {
            for (Condition c : conditions) {
                if (((memory & c.mask) == c.pattern)) {
                    return Move.COOPERATE;
                }
            }
            return Move.DEFECT;
        }

        @Override
        public void setOpponentsMove(Move m) {
            memory <<= 2;
            memory |= ((m == Move.COOPERATE) ? 1 : 0);
        }

    }

}
