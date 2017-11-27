package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.match.Score;
import com.mullekybernetik.gagame.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExhaustiveTournament implements Tournament {

    private MatchRunner matchRunner;

    public ExhaustiveTournament(MatchRunner matchRunner) {
        this.matchRunner = matchRunner;
    }

    public Table runTournament(List<Strategy> strategies, int roundsPerMatch) {
        Strategy[] strategiesArray = strategies.toArray(new Strategy[]{});
        AtomicInteger[] points = runTournamentInternal(strategiesArray, roundsPerMatch);
        return new Table(strategiesArray, points);
    }

    protected AtomicInteger[] runTournamentInternal(final Strategy[] strategies, final int roundsPerMatch) {

        final AtomicInteger[] totalPoints = new AtomicInteger[strategies.length];
        for (int i = 0; i < strategies.length; i++) {
            totalPoints[i] = new AtomicInteger(0);
        }

        List<Pairing> pairings = new ArrayList<>();
        for (int i = 0; i < strategies.length; i++) {
            for (int j = i + 1; j < strategies.length; j++) {
                pairings.add(new Pairing(i, j));
            }
        }

        pairings.parallelStream().forEach(p -> {
            Score score = matchRunner.runMatch(strategies[p.ai], strategies[p.bi], roundsPerMatch);
            totalPoints[p.ai].addAndGet(score.a);
            totalPoints[p.bi].addAndGet(score.b);
        });

        return totalPoints;
    }


    static private class Pairing {

        public final int ai;
        public final int bi;

        public Pairing(int ai, int bi) {
            this.ai = ai;
            this.bi = bi;
        }
    }

}