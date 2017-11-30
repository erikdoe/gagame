package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class DecisionTreeStrategy implements Strategy {

    private String stringRepresentation;
    private int strategy;
    private int depth;

    public DecisionTreeStrategy(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
        this.strategy = calcNumericalRepresentation(stringRepresentation);
        this.depth = calcDepth(stringRepresentation.length());
    }

    private static int calcNumericalRepresentation(String stringRepresentation) {
        int numericalRepresentation = 0;
        for (int i = stringRepresentation.length() - 1; i >= 0; i--) {
            numericalRepresentation <<= 1;
            numericalRepresentation |= (stringRepresentation.charAt(i) == '+') ? 1 : 0;
        }
        return numericalRepresentation;
    }

    private static int calcDepth(int size) {
        int depth = 0;
        int s = (size + 1) / 2;
        while ((s & 1) == 0) {
            depth += 1;
            s >>= 1;
        }
        if (s > 1)
            throw new IllegalArgumentException("Encoding length must be (2^x-1), was " + size);
        return depth;
    }

    protected int getNumericalRepresentation() {
        return strategy;
    }

    protected int getDepth() {
        return depth;
    }

    public Player instantiate() {
        return new PlayerImpl();
    }

    @Override
    public String toString() {
        String s = "";
        int j = 0;
        int bs = 1;
        for(int i = 0; i < stringRepresentation.length(); i++) {
            s += stringRepresentation.charAt(i) == '+' ? '\u25CF' : '\u25CB';
            j += 1;
            if ((j % bs == 0) && (i != stringRepresentation.length() - 1)) {
                s += "-";
                j = 0;
                bs <<= 1;
            }
        }

        return s;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DecisionTreeStrategy)
                && stringRepresentation.equals(((DecisionTreeStrategy)other).stringRepresentation);
    }


    protected class PlayerImpl implements Player {

        private int mdepth;
        private int memory;

        public Move getMove() {
            int shift = (1 << mdepth) - 1;
            int mask = (1 << (1 << mdepth)) - 1;

            int block = (strategy >> shift) & mask;
            int r = block & (1 << memory);

            if (mdepth < depth) {
                mdepth += 1;
            }

            return (r != 0) ? Move.COOPERATE : Move.DEFECT;
        }

        public void setOpponentsMove(Move m) {
            memory <<= 1;

            int mask = (1 << depth) - 1;
            memory &= mask;

            if (m == Move.COOPERATE) {
                memory |= 0x1;
            }
        }

        protected int getMemory() {
            return memory;
        }
    }

}
