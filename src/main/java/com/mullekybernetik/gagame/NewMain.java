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

    private static final int POPULATION_SIZE = 1000;
    private static final int SURVIVOR_PERCENTAGE = 85;
    private static final int TOURNAMENTS_PER_SEARCH = 40000;
    private static final int ROUNDS_PER_MATCH = 15;
    private static final int STRATEGY_DEPTH = 3;

    private static final Random random = new Random();

    public static void main(String[] args) {

        while (true) {

            Collection<Strategy> seedStrategies = createInitialSeedPopulation();

            for(int i = TOURNAMENTS_PER_SEARCH; i > 0; i--) {

                List<Strategy> population = new ArrayList<>();
                population.addAll(seedStrategies);
                int n = POPULATION_SIZE - seedStrategies.size();
                population.addAll(createRandomConditionalDefectors(n/2));
                population.addAll(createRandomDecisionTreeStrategies(n/2));

                Tournament tournament = new Tournament(new MatchRunner(), new AllPairingsFactory());
                Table result = tournament.runTournament(population, ROUNDS_PER_MATCH);

                seedStrategies = createNextSeedPopulation(result);

                logProgress(i, result);

            }

            System.out.print("\n**** NEW TOURNAMENT ****\n\n");
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
        for (TableEntry e : result.getTopEntries(POPULATION_SIZE * SURVIVOR_PERCENTAGE/100)) {
            strategies.add(e.getStrategy());
        }
        return strategies;
    }

    private static Collection<Strategy> createRandomDecisionTreeStrategies(int count) {
        StringGenerator generator = new StringGenerator("-+");
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int size = (1 << (random.nextInt(STRATEGY_DEPTH) + 2)) - 1;
            strategies.add(new DecisionTreeStrategy(generator.randomString(size)));
        }
        return strategies;
    }

    private static Collection<Strategy> createRandomConditionalDefectors(int count) {
        StringGenerator g0 = new StringGenerator("DCU?");
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int istrat = 0; istrat < count; istrat++) {
            int nblocks = 4;
            String[] blocks = new String[nblocks];
            for (int iblock = 0; iblock < nblocks; iblock++) {
                int ncond = random.nextInt(STRATEGY_DEPTH) + 1 + 1; // +1 boost to give advantage over tree breadth
                String cond = g0.randomString(ncond);
                int uidx = cond.lastIndexOf('U');
                if (uidx > 0) {
                    cond = cond.substring(uidx, cond.length());
                }
                blocks[iblock] = cond;
            }

            String stringRepresentation = String.join("-", blocks);
            strategies.add(new ConditionalCooperator(stringRepresentation));
        }
        return strategies;
    }

    private static void logProgress(int i, Table result) {
//        if (i % (TOURNAMENTS_PER_SEARCH / 20) == 0) {
//            System.out.print(".");
//        }
        if (i % 100 == 0) {
            System.out.format("%6d  ", i);
            for (TableEntry e : result.getTopEntries(20))
                System.out.format("%-20s: %5d  ", e.getStrategy(), e.getPoints());
            System.out.println();
        }
    }

}
