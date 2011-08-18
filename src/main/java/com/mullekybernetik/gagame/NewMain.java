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

    private static int PLAYERCOUNT = 100;
    private static int ROUNDS_PER_MATCH = 50;
    private static int TOURNAMENT_RUNS = 1;

    private static Random generator = new Random();

    public static void main(String[] args) {
        HashMap<Strategy, Integer> wins = new HashMap<Strategy, Integer>();

        for (int run = 0; run < 1000000; run++) {
            Strategy[] players = new Strategy[PLAYERCOUNT];
            for (int i = 0; i < PLAYERCOUNT; i++)
                players[i] = getRandomPlayer();
            Tournament tournament = new ExhaustiveTournament(new Referee(), ROUNDS_PER_MATCH);
            ScoreTable result = tournament.runTournament(players, TOURNAMENT_RUNS);
            Strategy winner = result.getTopScores(1).iterator().next().getStrategy();
            Integer points = wins.get(winner);
            points = (points == null) ? 1 : points + 1;
            wins.put(winner, points);
            if (run % 100 != 0)
                continue;
            Score[] scores = new Score[wins.size()];
            int i = 0;
            for (Strategy strategy : wins.keySet())
                scores[i++] = new Score(strategy, wins.get(strategy));
            ScoreTable table = new ScoreTable(scores);
            List<Score> topten = new ArrayList<Score>(table.getTopScores(20));
            Collections.reverse(topten);
            for (Score score : topten)
                System.out.format("%4d %-20s ", score.getPoints(), score.getStrategy().toString());
            System.out.println();
        }
    }

    public static Strategy getRandomPlayer() {
        int r = generator.nextInt(3 + 65536);
        switch (r) {
            case 0:
                return new Defector();
            case 1:
                return new Cooperator();
            case 2:
                return new TitForTat();
            default:
                return getTreeEncoded(r - 3);
        }
    }

    private static Strategy getTreeEncoded(int num) {
        char[] encoding = new char[16];
        for (int i = 0; i < encoding.length; i++) {
            encoding[i] = ((num & 1) == 1) ? 'C' : 'D';
            num >>= 1;
        }
        return new TreeEncoded(new String(encoding));
    }

}
