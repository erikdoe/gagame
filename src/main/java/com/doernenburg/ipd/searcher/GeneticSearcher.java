package com.doernenburg.ipd.searcher;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;
import com.doernenburg.ipd.tournament.Table;

import java.util.*;

public class GeneticSearcher extends Searcher {

    private static final int REPLACEMENT_PERCENTAGE = 30;
    private static final int NUM_NEW_RANDOMS = 2;

    private final Random random;

    public GeneticSearcher(StrategyFactory factory) {
        super(factory);
        this.random = new Random();
    }

    @Override
    public List<Strategy> createNextPopulation(int populationSize, Table result) {
        List<Strategy> population = new ArrayList<>();
        List<Strategy> encoded = new ArrayList<>();

        // copy everything but the worst strategies
        for (Table.Entry e : result.getTopEntries(populationSize * (100 - REPLACEMENT_PERCENTAGE) / 100)) {
            population.add(e.getStrategy());
        }

        // add some new random strategies
        population.addAll(factory.getRandomStrategies(NUM_NEW_RANDOMS));

        // collect all encoded strategies so far
        for (Strategy s : population) {
            if (s.getClass() == factory.getStrategyClass()) {
                encoded.add(s);
            }
        }

        // fill with strategies based on the best (here: copy basics, encoded get recombined with other top strategies)
        for (Table.Entry e : result.getTopEntries(populationSize * REPLACEMENT_PERCENTAGE / 100 - NUM_NEW_RANDOMS)) {
            if (e.getStrategy().getClass() == factory.getStrategyClass()) {
                Strategy a = e.getStrategy();
                Strategy b = encoded.get(random.nextInt(encoded.size()));
                population.add(factory.recombine(a, b));
            } else {
                population.add(instantiateStrategy(e.getStrategy().getClass()));
            }
        }

        return population;
    }

}
