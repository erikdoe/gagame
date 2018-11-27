package com.doernenburg.ipd.match;

import com.doernenburg.ipd.strategies.Player;

import java.util.SplittableRandom;

public class Match {

    public static final int POINTS_FOR_SUCCESSFUL_BETRAYAL = 5;
    public static final int POINTS_FOR_SUCCESSFUL_COOPERATION = 3;
    public static final int POINTS_FOR_MUTUAL_BETRAYAL = 1;
    public static final int POINTS_FOR_UNSUCCESSFUL_COOPERATION = 0;

    private static final SplittableRandom baseRandom = new SplittableRandom();

    private final Player a;
    private final Player b;
    private final Score score;
    private final SplittableRandom random;

    public Match(Player a, Player b) {
        this.a = a;
        this.b = b;
        this.score = new Score();
        this.random = baseRandom.split();
    }

    public Score playMatch(int games, double mistakeProb) {
        for (int i = 0; i < games; i++) {
            playGame(mistakeProb);
        }
        return score;
    }

    protected void playGame(double mistakeProb) {
        Move moveA = a.getMove();
        if (random.nextDouble() < mistakeProb) {
            moveA = (moveA == Move.COOPERATE) ? Move.DEFECT : Move.COOPERATE;
        }
        Move moveB = b.getMove();
        if (random.nextDouble() < mistakeProb) {
            moveB = (moveB == Move.COOPERATE) ? Move.DEFECT : Move.COOPERATE;
        }
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
