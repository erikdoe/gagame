package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.Referee;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.*;
import com.mullekybernetik.gagame.tournament.ExhaustiveTournament;
import com.mullekybernetik.gagame.tournament.Score;
import com.mullekybernetik.gagame.tournament.ScoreTable;
import com.mullekybernetik.gagame.tournament.Tournament;

import java.util.*;

public class NewMain {

    private static int ROUNDS_PER_MATCH = 6;
    private static int STRATEGY_SIZE = 16;
    private static int STRATEGY_COUNT = 100;

    private static Random generator = new Random();

    public static void main(String[] args) {

        while (true) {

            Map<Strategy, Integer> wins = new HashMap<Strategy, Integer>();

            for (int run = 0; run < 50000; run++) {
                Strategy[] strategies = createRandomStrategies(STRATEGY_COUNT);
                Tournament tournament = new ExhaustiveTournament(new Referee(), ROUNDS_PER_MATCH);
                ScoreTable result = tournament.runTournament(strategies);

                Collection<Score> top10 = result.getTopScores(10);
                for (Score s : top10) {
                    Integer points = wins.get(s.getStrategy());
                    points = ((points == null) ? 0 : points) + s.getPoints();
                    wins.put(s.getStrategy(), points);
                }
            }

            Score[] scores = new Score[wins.size()];
            int i = 0;
            for (Strategy strategy : wins.keySet())
                scores[i++] = new Score(strategy, wins.get(strategy));
            ScoreTable table = new ScoreTable(scores);

            List<Score> seasonTop20 = new ArrayList<Score>(table.getTopScores(20));
            Collections.reverse(seasonTop20);
            for (Score score : seasonTop20)
                System.out.format("%4d %-20s ", score.getPoints(), score.getStrategy().toString());
            System.out.println();
        }
    }

    private static Strategy[] createAllStrategies() {
        Strategy[] strategies = new Strategy[4 + (1 << STRATEGY_SIZE)];
        for (int i = 0; i < strategies.length; i++)
            strategies[i] = getStrategyForNumber(i);
        return strategies;
    }

    private static Strategy[] createRandomStrategies(int count) {
        Strategy[] strategies = new Strategy[count];
        for (int i = 0; i < strategies.length; i++) {
            int r = generator.nextInt(4 + (1 << STRATEGY_SIZE));
            strategies[i] = getStrategyForNumber(r);
        }
        return strategies;
    }

    private static Strategy getStrategyForNumber(int r) {
        switch (r) {
            case 0:
                return new Defector();
            case 1:
                return new Cooperator();
            case 2:
                return new TitForTat();
            case 3:
                return new RandomChoice();
            default:
                return TreeEncoded.fromNumber(r - 4, STRATEGY_SIZE);
        }
    }

}
