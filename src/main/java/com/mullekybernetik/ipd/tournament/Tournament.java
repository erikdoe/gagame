package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.match.MatchRunner;
import com.mullekybernetik.ipd.match.Score;
import com.mullekybernetik.ipd.strategies.Strategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament {

    private MatchRunner matchRunner;
    private PairingFactory pairingFactory;

    public Tournament(MatchRunner matchRunner, PairingFactory pairingFactory) {
        this.matchRunner = matchRunner;
        this.pairingFactory = pairingFactory;
    }

    public Table runTournament(List<Strategy> strategies, int roundsPerMatch) {
        Strategy[] strategiesArray = strategies.toArray(new Strategy[]{});
        List<Pairing> pairings = pairingFactory.getPairingsForStrategies(strategies);
        AtomicInteger[] points = runTournamentInternal(strategiesArray, pairings, roundsPerMatch);
        return new Table(strategiesArray, points);
    }

    protected AtomicInteger[] runTournamentInternal(final Strategy[] strategies, List<Pairing> pairings, final int roundsPerMatch) {

        final AtomicInteger[] totalPoints = new AtomicInteger[strategies.length];
        for (int i = 0; i < strategies.length; i++) {
            totalPoints[i] = new AtomicInteger(0);
        }

        pairings.parallelStream().forEach(p -> {
            Score score = matchRunner.runMatch(strategies[p.ai], strategies[p.bi], roundsPerMatch);
            totalPoints[p.ai].addAndGet(score.a);
            totalPoints[p.bi].addAndGet(score.b);
        });

        return totalPoints;
    }

}

