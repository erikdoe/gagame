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

    public ScoreTable runTournament(Strategy[] participants, int runs) {
        return new ScoreTable(runTournamentInternal(participants, runs));
    }

    public Score[] runTournamentInternal(Strategy[] participants, int runs) {
        Score[] scores = new Score[participants.length];
        for (int i = 0; i < participants.length; i++)
            scores[i] = new Score(participants[i]);

        for (int r = 0; r < runs; r++) {
            for (int i = 0; i < participants.length; i++) {
                for (int j = i + 1; j < participants.length; j++) {
                    int points[] = referee.runMatch(participants[i], participants[j], rounds);
                    scores[i].addToPoints(points[0]);
                    scores[j].addToPoints(points[1]);
                }
            }
        }
        return scores;
    }

}