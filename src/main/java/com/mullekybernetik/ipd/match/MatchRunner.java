package com.mullekybernetik.ipd.match;

import com.mullekybernetik.ipd.strategies.Strategy;

public class MatchRunner {

    public Score runMatch(Strategy a, Strategy b, int rounds) {
        Match m = new Match(a.instantiate(), b.instantiate());
        for (int i = 0; i < rounds; i++) {
            m.playRound();
        }
        return m.getScore();
    }

}