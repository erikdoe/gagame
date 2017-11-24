package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class TreeEncoded extends StrategyBase implements Strategy {

    private int depth;
    private String strategyString;
    private Move[] strategy;
    private Move[] memory;
    private int memoryPtr;

    /* 
    * The encoding can be understood as labels at the top of a decision tree. The last move is at the
    * root of the tree and 'defect' means to take the left branch. Thus, if the last move of the opponent is
    * a 'defect' the next move can be found in the left half of the strategy. If all previous moves of the
    * opponent were 'cooperate' then the next move is the rightmost move in the left half.
    *
    * If we had the encoded strategy 'DDDCDDDD' the tree would be like this:
    *
    *             D D D C D D D D
    *     L1       *  (*)   *   *
    *     L2        (*)       *
    *     L3            (*)
    *
    * For the example above we would go left in L3, right in L2, and right again in L1, resulting in C as a
    * response to the opponent's move sequence of CCD.
    *
    * Note that this encoding keeps the outcomes close together that have similar recent moves, or in other
    * words the most recent moves have the greatest spatial effect.
    *
    */

    // maybe add third letter "R" for "make random choice"
    // allow variable length strategies, then D, C, DC, RR are standard strategies
    // ^ what happens with strategy in first move, i.e. is DC really tit for tat?

    public static TreeEncoded fromNumber(int number, int bits) {
        char[] encoding = new char[bits];
        for (int i = 0; i < encoding.length; i++) {
            encoding[i] = ((number & 1) == 1) ? '+' : '-';
            number >>= 1;
        }
        return new TreeEncoded(new String(encoding));
    }


    protected TreeEncoded(String encoding) {
        depth = calcDepth(encoding.length());
        this.strategyString = encoding;
        strategy = new Move[1 << depth];
        for (int i = 0; i < strategy.length; i++)
            strategy[i] = (encoding.charAt(i) == '+') ? Move.COOPERATE : Move.DEFECT;
    }

    private TreeEncoded(int depth, String strategyString, Move[] strategy) {
        this.depth = depth;
        this.strategyString = strategyString;
        this.strategy = strategy;
    }

    private int calcDepth(int size) {
        int depth = 0;
        for (; (size & 1) == 0; size >>= 1)
            depth += 1;
        if (size > 1)
            throw new IllegalArgumentException("Encoding length must be a power of 2, was " + size);
        return depth;
    }

    public Move[] getEncodedStrategy() {
        return strategy;
    }

    public void newMatch() {
        memory = new Move[depth];
        for (int i = 0; i < memory.length; i++)
            memory[i] = Move.DEFECT;
        memoryPtr = 0;
    }

    public void setOpponentsMove(Move m) {
        memoryPtr = (memoryPtr + (depth - 1)) % depth;
        memory[memoryPtr] = m;
    }

    public Move getMove() {
        int position = 0;
        int p = memoryPtr;
        for (int i = 0; i < depth; i++) {
            position *= 2;
            if (memory[p] == Move.COOPERATE)
                position += 1;
            p = (p + 1) % depth;
        }
        return strategy[position];
    }

    public Strategy clone() {
        return new TreeEncoded(depth, strategyString, strategy);
    }

    @Override
    public String toString() {
        return "[" + strategyString + "]";
    }


}
