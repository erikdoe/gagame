package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.match.Match;
import com.doernenburg.ipd.match.Score;
import com.doernenburg.ipd.strategies.Strategy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament {

    private final PairingFactory pairingFactory;
    private final int gamesPerMatch;
    private final double mistakeProb;

    public Tournament(PairingFactory pairingFactory, int gamesPerMatch, double mistakeProb) {
        this.pairingFactory = pairingFactory;
        this.gamesPerMatch = gamesPerMatch;
        this.mistakeProb = mistakeProb;
    }

    public Table runTournament(List<Strategy> strategies) {
        Strategy[] strategiesArray = strategies.toArray(new Strategy[0]);
        List<Pairing> pairings = pairingFactory.getPairingsForStrategies(strategies);
        AtomicInteger[] points = runTournamentInternal(strategiesArray, pairings);
        return new Table(strategiesArray, points);
    }

    private AtomicInteger[] runTournamentInternal(final Strategy[] strategies, List<Pairing> pairings) {

        final AtomicInteger[] totalPoints = new AtomicInteger[strategies.length];
        Arrays.setAll(totalPoints, i -> new AtomicInteger(0));

        pairings.parallelStream().forEach(p -> {
            Match match = new Match(strategies[p.aIdx].instantiate(), strategies[p.bIdx].instantiate());
            Score score = match.playMatch(gamesPerMatch, mistakeProb);
            totalPoints[p.aIdx].addAndGet(score.a);
            totalPoints[p.bIdx].addAndGet(score.b);
        });

        return totalPoints;
    }

}

