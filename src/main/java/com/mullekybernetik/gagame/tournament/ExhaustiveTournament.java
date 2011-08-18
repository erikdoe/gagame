package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Referee;
import com.mullekybernetik.gagame.match.Strategy;

public class ExhaustiveTournament implements Tournament {

    private Referee referee;
    private int rounds;

    public ExhaustiveTournament(Referee referee, int rounds) {
        this.referee = referee;
        this.rounds = rounds;
    }

    public ScoreTable runTournament(Strategy[] strategies) {
        return new ScoreTable(runTournamentInternal(strategies));
    }

    public Score[] runTournamentInternal(Strategy[] strategies) {
        Score[] scores = new Score[strategies.length];
        for (int i = 0; i < strategies.length; i++)
            scores[i] = new Score(strategies[i]);

        for (int i = 0; i < strategies.length; i++) {
            for (int j = i + 1; j < strategies.length; j++) {
                int points[] = referee.runMatch(strategies[i], strategies[j], rounds);
                scores[i].addToPoints(points[0]);
                scores[j].addToPoints(points[1]);
            }
        }
        return scores;
    }

}