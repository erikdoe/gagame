package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.match.Match;
import com.mullekybernetik.ipd.match.Score;
import com.mullekybernetik.ipd.strategies.Strategy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament {

    private final PairingFactory pairingFactory;

    public Tournament(PairingFactory pairingFactory) {
        this.pairingFactory = pairingFactory;
    }

    public Table runTournament(List<Strategy> strategies, int roundsPerMatch) {
        Strategy[] strategiesArray = strategies.toArray(new Strategy[strategies.size()]);
        List<Pairing> pairings = pairingFactory.getPairingsForStrategies(strategies);
        AtomicInteger[] points = runTournamentInternal(strategiesArray, pairings, roundsPerMatch);
        return new Table(strategiesArray, points);
    }

    private AtomicInteger[] runTournamentInternal(final Strategy[] strategies, List<Pairing> pairings, final int roundsPerMatch) {

        final AtomicInteger[] totalPoints = new AtomicInteger[strategies.length];
        Arrays.setAll(totalPoints, i -> new AtomicInteger(0));

        pairings.parallelStream().forEach(p -> {
            Match match = new Match(strategies[p.ai].instantiate(), strategies[p.bi].instantiate());
            Score score = match.playGame(roundsPerMatch);
            totalPoints[p.ai].addAndGet(score.a);
            totalPoints[p.bi].addAndGet(score.b);
        });

        return totalPoints;
    }

}

