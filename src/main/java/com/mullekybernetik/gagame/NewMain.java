package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.Referee;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import com.mullekybernetik.gagame.strategies.TitForTat;
import com.mullekybernetik.gagame.strategies.TreeEncoded;
import com.mullekybernetik.gagame.tournament.ExhaustiveTournament;
import com.mullekybernetik.gagame.tournament.Score;
import com.mullekybernetik.gagame.tournament.ScoreTable;
import com.mullekybernetik.gagame.tournament.Tournament;

import java.util.*;

public class NewMain {

    private static int ROUNDS_PER_MATCH = 7;
    private static int STRATEGY_SIZE = 8;

    private static Random generator = new Random();

    public static void main(String[] args) {

        while (true) {

            Map<Strategy, Integer> wins = new HashMap<Strategy, Integer>();
            Strategy[] strategies = createStrategies();

            for (int run = 0; run < 10; run++) {
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

    private static Strategy[] createStrategies() {
        Strategy[] players = new Strategy[3 + (1 << STRATEGY_SIZE)];
        for (int i = 0; i < players.length; i++)
            players[i] = getPlayerForNumber(i);
        return players;
    }

    public static Strategy getRandomPlayer() {
        int r = generator.nextInt(3 + 65536);
        return getPlayerForNumber(r);
    }

    private static Strategy getPlayerForNumber(int r) {
        switch (r) {
            case 0:
                return new Defector();
            case 1:
                return new Cooperator();
            case 2:
                return new TitForTat();
            default:
                return TreeEncoded.fromNumber(r - 3, STRATEGY_SIZE);
        }
    }

}
