package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.strategies.Strategy;
import com.mullekybernetik.gagame.strategies.*;
import com.mullekybernetik.gagame.tournament.AllPairingsFactory;
import com.mullekybernetik.gagame.tournament.Table;
import com.mullekybernetik.gagame.tournament.TableEntry;
import com.mullekybernetik.gagame.tournament.Tournament;
import com.mullekybernetik.gagame.util.StringGenerator;

import java.util.*;

public class NewMain {

    private static final int POPULATION_SIZE = 500;
    private static final int SURVIVOR_PERCENTAGE = 80;
    private static final int TOURNAMENTS_PER_SEARCH = 2000;
    private static final int ROUNDS_PER_MATCH = 20;

    private static final Random random = new Random();

    public static void main(String[] args) {

        while (true) {

            Collection<Strategy> seedStrategies = createInitialSeedPopulation();

            for(int i = TOURNAMENTS_PER_SEARCH; i > 0; i--) {

                List<Strategy> population = new ArrayList<>();
                population.addAll(seedStrategies);
                population.addAll(createRandomEncodedStrategies(POPULATION_SIZE - seedStrategies.size()));

                Tournament tournament = new Tournament(new MatchRunner(), new AllPairingsFactory());
                Table result = tournament.runTournament(population, ROUNDS_PER_MATCH);

                seedStrategies = createNextSeedPopulation(result);

                logProgress(i, result);

            }
        }
    }

    private static Collection<Strategy> createInitialSeedPopulation() {
        Collection<Strategy> strategies = new ArrayList<>();
//        for (int i = 0; i < POPULATION_SIZE / 2 / 3; i++) {
//            strategies.add(new Cooperator());
//            strategies.add(new Defector());
//            strategies.add(new TitForTat());
//        }
        return strategies;
    }

    private static Collection<Strategy> createNextSeedPopulation(Table result) {
        Collection<Strategy> strategies = new ArrayList<>();
        for (TableEntry e : result.getTopEntries(POPULATION_SIZE * SURVIVOR_PERCENTAGE/100)) {
            strategies.add(e.getStrategy());
        }
        return strategies;
    }

    private static Collection<Strategy> createRandomEncodedStrategies(int count) {
        StringGenerator generator = new StringGenerator("-+");
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int size = (1 << (random.nextInt(3) + 3)) - 1;
            strategies.add(new EncodedStrategy(generator.randomString(size)));
        }
        return strategies;
    }

    private static void logProgress(int i, Table result) {
        if (i % (TOURNAMENTS_PER_SEARCH / 20) == 0) {
            System.out.print(".");
        }
        if (i == 1) {
            System.out.print(" ");
            for (TableEntry e : result.getTopEntries(20))
                System.out.format("%-36s: %5d  ", e.getStrategy(), e.getPoints());
            System.out.println();
        }
    }

}
