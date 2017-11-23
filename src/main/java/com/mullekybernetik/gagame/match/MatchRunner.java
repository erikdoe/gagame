package com.mullekybernetik.gagame.match;

public class MatchRunner {

    public Score runMatch(Strategy a, Strategy b, int rounds) {
        Match m = new Match(a, b);
        for (int i = 0; i < rounds; i++) {
            m.playRound();
        }
        return m.getScore();
    }

}