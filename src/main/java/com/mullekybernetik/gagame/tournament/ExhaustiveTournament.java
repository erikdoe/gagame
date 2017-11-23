package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.match.Score;
import com.mullekybernetik.gagame.match.Strategy;

public class ExhaustiveTournament implements Tournament {

    private MatchRunner matchRunner;

    public ExhaustiveTournament(MatchRunner matchRunner) {
        this.matchRunner = matchRunner;
    }

    public Table runTournament(Strategy[] strategies, int roundsPerMatch) {
        int[] points = runTournamentInternal(strategies, roundsPerMatch);
        return new Table(strategies, points);
    }

    protected int[] runTournamentInternal(Strategy[] strategies, int roundsPerMatch) {
        int[] totalPoints = new int[strategies.length];

        for (int i = 0; i < strategies.length; i++) {
            for (int j = i + 1; j < strategies.length; j++) {
                Score score = matchRunner.runMatch(strategies[i], strategies[j], roundsPerMatch);
                totalPoints[i] += score.a;
                totalPoints[j] += score.b;
            }
        }
        return totalPoints;
    }

}