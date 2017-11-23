package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.*;
import com.mullekybernetik.gagame.tournament.ExhaustiveTournament;
import com.mullekybernetik.gagame.tournament.Table;
import com.mullekybernetik.gagame.tournament.TableEntry;
import com.mullekybernetik.gagame.tournament.Tournament;

import java.lang.reflect.Array;
import java.util.*;

public class NewMain {

    private static final int ROUNDS_PER_MATCH = 7;
    private static final int STRATEGY_SIZE = 8;

    private static Random random = new Random();

    public static void main(String[] args) {

        while (true) {

            Map<Strategy, Integer> totalPoints = new HashMap<>();

            for (int run = 0; run < 10; run++) {
                Strategy[] strategies = createAllStrategies();
                Tournament tournament = new ExhaustiveTournament(new MatchRunner());
                Table result = tournament.runTournament(strategies, ROUNDS_PER_MATCH);

                Collection<TableEntry> topEntries = result.getAllEntries();
                for (TableEntry e : topEntries) {
                    Integer count = totalPoints.get(e.getStrategy());
                    count = ((count == null) ? 0 : count) + e.getPoints();
                    totalPoints.put(e.getStrategy(), count);
                }
            }

            ArrayList<TableEntry> entries = new ArrayList<>();
            for (Strategy strategy : totalPoints.keySet())
                entries.add(new TableEntry(strategy, totalPoints.get(strategy)));
            Table table = new Table(entries);

            List<TableEntry> seasonTop20 = new ArrayList<>(table.getTopEntries(20));
            Collections.reverse(seasonTop20);
            for (TableEntry entry : seasonTop20)
                System.out.format("%-10s: %4d    ", entry.getStrategy().toString(), entry.getPoints());
            System.out.println();
        }
    }

    private static Strategy[] createAllStrategies() {
        Strategy[] strategies = new Strategy[(1 << STRATEGY_SIZE)];
        for (int i = 0; i < strategies.length; i++)
            strategies[i] = getStrategyForNumber(i+4);
        return strategies;
    }

    private static Strategy[] createRandomStrategies(int count) {
        Strategy[] strategies = new Strategy[count];
        for (int i = 0; i < strategies.length; i++) {
            int r = random.nextInt(4 + (1 << STRATEGY_SIZE));
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
