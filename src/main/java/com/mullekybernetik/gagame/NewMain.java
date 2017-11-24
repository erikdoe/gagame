package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.strategies.Strategy;
import com.mullekybernetik.gagame.strategies.*;
import com.mullekybernetik.gagame.tournament.ExhaustiveTournament;
import com.mullekybernetik.gagame.tournament.Table;
import com.mullekybernetik.gagame.tournament.TableEntry;
import com.mullekybernetik.gagame.tournament.Tournament;

import java.util.*;

public class NewMain {

    private static final int POPULATION_SIZE = 1000;
    private static final int TOURNAMENTS_PER_SEARCH = 400;
    private static final int ROUNDS_PER_MATCH = 7;
    private static final int STRATEGY_SIZE = 16;

    private static Random random = new Random();

    public static void main(String[] args) {


        while (true) {

            Collection<Strategy> seedStrategies = createInitialSeedPopulation();

            for(int i = TOURNAMENTS_PER_SEARCH; i > 0; i--) {

                List<Strategy> population = new ArrayList<>();
                population.addAll(seedStrategies);
                population.addAll(createRandomTreeStrategies(POPULATION_SIZE - seedStrategies.size()));

                Tournament tournament = new ExhaustiveTournament(new MatchRunner());
                Table result = tournament.runTournament(population, ROUNDS_PER_MATCH);

                seedStrategies = createNextSeedPopulation(result);

                logProgress(i, result);

            }
        }
    }

    private static Collection<Strategy> createInitialSeedPopulation() {
        Collection<Strategy> strategies = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE / 2 / 3; i++) {
            strategies.add(new Cooperator());
            strategies.add(new Defector());
            strategies.add(new TitForTat());
        }
        return strategies;
    }

    private static Collection<Strategy> createNextSeedPopulation(Table result) {
        Collection<Strategy> strategies = new ArrayList<>();
        for (TableEntry e : result.getTopEntries(POPULATION_SIZE * 60/100)) {
            strategies.add(e.getStrategy());
        }
        return strategies;
    }

    private static Collection<Strategy> createRandomTreeStrategies(int count) {
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int r = random.nextInt(1 << STRATEGY_SIZE);
            strategies.add(TreeEncoded.fromNumber(r, STRATEGY_SIZE));
        }
        return strategies;
    }

    private static void logProgress(int i, Table result) {
        if (i % 20 == 0) {
            System.out.print(".");
        }
        if (i == 1) {
            System.out.print(" ");
            for (TableEntry e : result.getTopEntries(20))
                System.out.format("%-20s: %5d  ", e.getStrategy(), e.getPoints());
            System.out.println();
        }
    }

}
