package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.tournament.Score;
import com.mullekybernetik.gagame.tournament.ScoreTable;

public class TournamentMain {

    private static int PLAYERCOUNT = 100;
    private static int ROUNDS_PER_MATCH = 20;
    private static int TOURNAMENT_RUNS = 1;

    public static void main(String[] args) {
        do {
            RandomCodedStrategyRunner runner = new RandomCodedStrategyRunner(PLAYERCOUNT);
            ScoreTable results = runner.runTournaments(TOURNAMENT_RUNS, ROUNDS_PER_MATCH);
            printTop(results);
        }
        while (true);
    }

    public static void printTop(ScoreTable results) {
        for (Score s : results.getTopScores(100))
            System.out.printf("%-18s %d - ", s.getStrategy(), s.getPoints());
        System.out.println();
    }

    public static void printAllInOneLine(ScoreTable results) {
        for (Score s : results.getAllScores())
            System.out.print(s.getStrategy().toString().charAt(0));
        System.out.println();
    }

}