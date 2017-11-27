package com.mullekybernetik.gagame.match;

import com.mullekybernetik.gagame.strategies.Strategy;

public class MatchRunner {

    public Score runMatch(Strategy a, Strategy b, int rounds) {
        Match m = new Match(a.instantiate(), b.instantiate());
        for (int i = 0; i < rounds; i++) {
            m.playRound();
        }
        return m.getScore();
    }

}