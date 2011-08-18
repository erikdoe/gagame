package com.mullekybernetik.gagame.match;

public class Referee {

    private static int[] CC_POINTS = new int[]{3, 3};
    private static int[] CD_POINTS = new int[]{0, 5};
    private static int[] DC_POINTS = new int[]{5, 0};
    private static int[] DD_POINTS = new int[]{1, 1};

    protected Strategy a, b;
    protected int[] points;

    public int[] runMatch(Strategy a, Strategy b, int rounds) {
        setupMatch(a, b);
        doRounds(rounds);
        return points;
    }

    private void setupMatch(Strategy a, Strategy b) {
        this.a = a;
        this.b = b;
        points = new int[2];
        a.newMatch();
        b.newMatch();
    }

    private void doRounds(int count) {
        for (int i = 0; i < count; i++)
            doRound();
    }

    protected void doRound() {
        Move moveA = a.getMove();
        Move moveB = b.getMove();
        a.setOpponentsMove(moveB);
        b.setOpponentsMove(moveA);
        addPoints(calcPoints(moveA, moveB));
    }

    private int[] calcPoints(Move thisMove, Move otherMove) {
        if (thisMove == Move.COOPERATE)
            return (otherMove == Move.COOPERATE) ? CC_POINTS : CD_POINTS;
        else
            return (otherMove == Move.COOPERATE) ? DC_POINTS : DD_POINTS;
    }

    private void addPoints(int[] y) {
        points[0] += y[0];
        points[1] += y[1];
    }

}