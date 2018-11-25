package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.match.Match;
import com.doernenburg.ipd.match.Score;
import com.doernenburg.ipd.strategies.Strategy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament {

    private final PairingFactory pairingFactory;

    public Tournament(PairingFactory pairingFactory) {
        this.pairingFactory = pairingFactory;
    }

    public Table runTournament(List<Strategy> strategies, int roundsPerMatch) {
        Strategy[] strategiesArray = strategies.toArray(new Strategy[0]);
        List<Pairing> pairings = pairingFactory.getPairingsForStrategies(strategies);
        AtomicInteger[] points = runTournamentInternal(strategiesArray, pairings, roundsPerMatch);
        return new Table(strategiesArray, points);
    }

    private AtomicInteger[] runTournamentInternal(final Strategy[] strategies, List<Pairing> pairings, final int roundsPerMatch) {

        final AtomicInteger[] totalPoints = new AtomicInteger[strategies.length];
        Arrays.setAll(totalPoints, i -> new AtomicInteger(0));

        pairings.parallelStream().forEach(p -> {
            Match match = new Match(strategies[p.aIdx].instantiate(), strategies[p.bIdx].instantiate());
            Score score = match.playGame(roundsPerMatch);
            totalPoints[p.aIdx].addAndGet(score.a);
            totalPoints[p.bIdx].addAndGet(score.b);
        });

        return totalPoints;
    }

}

