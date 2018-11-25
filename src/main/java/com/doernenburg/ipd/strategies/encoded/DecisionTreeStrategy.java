package com.doernenburg.ipd.strategies.encoded;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import com.doernenburg.ipd.strategies.Strategy;

/*

    The strategy is encoded as a decision tree, e.g.

        C     D     D     D
           C           D
                 C

     - If the last move was cooperate, go left
     - If the last move was defect, go right
     - If the last move is not available or tree ends, use value at node

     The nodes are encoded sequentially by level, e.g.

        3     4     5     6
           1           2
                 0

 */

public class DecisionTreeStrategy implements Strategy {

    private final String stringRepresentation;
    private final String niceStringRepresentation;
    private final int strategy;
    private final int depth;

    public DecisionTreeStrategy(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
        this.niceStringRepresentation = beautifyStringRepresentation(stringRepresentation);
        this.strategy = calcNumericalRepresentation(stringRepresentation);
        this.depth = calcDepth(stringRepresentation.length());
    }

    private static String beautifyStringRepresentation(String original) {
        String beautified = "";
        int j = 0;
        int bs = 1;
        for(int i = 0; i < original.length(); i++) {
            beautified += original.charAt(i) == 'C' ? '+' : '-'; //            '\u25CF' : '\u25CB';
            j += 1;
            if ((j % bs == 0) && (i != original.length() - 1)) {
                beautified += "/";
                j = 0;
                bs <<= 1;
            }
        }
        return beautified;
    }

    private static int calcNumericalRepresentation(String stringRepresentation) {
        int numericalRepresentation = 0;
        for (int i = stringRepresentation.length() - 1; i >= 0; i--) {
            numericalRepresentation <<= 1;
            numericalRepresentation |= (stringRepresentation.charAt(i) == 'C') ? 1 : 0;
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

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    protected int getNumericalRepresentation() {
        return strategy;
    }

    protected int getDepth() {
        return depth;
    }

    @Override
    public Player instantiate() {
        return new PlayerImpl();
    }

    @Override
    public String toString() {
        return niceStringRepresentation;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DecisionTreeStrategy)
                && stringRepresentation.equals(((DecisionTreeStrategy)other).stringRepresentation);
    }


    protected class PlayerImpl implements Player {

        private int mcount;
        private int memory;

        @Override
        public Move getMove() {
            int shift = (1 << mcount) - 1;
            int mask = (1 << (1 << mcount)) - 1;

            int block = (strategy >> shift) & mask;
            int r = block & (1 << memory);

            if (mcount < depth) {
                mcount += 1;
            }

            return (r != 0) ? Move.COOPERATE : Move.DEFECT;
        }

        @Override
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
