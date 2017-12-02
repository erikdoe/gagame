package com.mullekybernetik.ipd.match;

import com.mullekybernetik.ipd.strategies.Player;

public class Match {

    public static final int POINTS_FOR_SUCCESSFUL_BETRAYAL = 3;
    public static final int POINTS_FOR_SUCCESSFUL_COOPERATION = 2;
    public static final int POINTS_FOR_MUTUAL_BETRAYAL = 1;
    public static final int POINTS_FOR_UNSUCCESSFUL_COOPERATION = 0;

    private final Player a;
    private final Player b;
    private Score score;

    public Match(Player a, Player b) {
        this.a = a;
        this.b = b;
        this.score = new Score();
    }

    public void playRound() {
        Move moveA = a.getMove();
        Move moveB = b.getMove();
        a.setOpponentsMove(moveB);
        b.setOpponentsMove(moveA);
        updateScore(moveA, moveB);
    }

    private void updateScore(Move moveA, Move moveB) {
        if ((moveA == Move.COOPERATE) && (moveB == Move.COOPERATE)) {
            score.a += POINTS_FOR_SUCCESSFUL_COOPERATION;
            score.b += POINTS_FOR_SUCCESSFUL_COOPERATION;
        } else if ((moveA == Move.COOPERATE) && (moveB == Move.DEFECT)) {
            score.a += POINTS_FOR_UNSUCCESSFUL_COOPERATION;
            score.b += POINTS_FOR_SUCCESSFUL_BETRAYAL;
        } else if ((moveA == Move.DEFECT) && (moveB == Move.COOPERATE)) {
            score.a += POINTS_FOR_SUCCESSFUL_BETRAYAL;
            score.b += POINTS_FOR_UNSUCCESSFUL_COOPERATION;
        } else if ((moveA == Move.DEFECT) && (moveB == Move.DEFECT)) {
            score.a += POINTS_FOR_MUTUAL_BETRAYAL;
            score.b += POINTS_FOR_MUTUAL_BETRAYAL;
        }
    }

    public Score getScore() {
        return score;
    }

}