package com.doernenburg.ipd.searcher;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;
import com.doernenburg.ipd.tournament.Table;

import java.util.*;
import java.util.stream.Collectors;

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
        copyBestStrategies(result, populationSize * (100 - REPLACEMENT_PERCENTAGE) / 100, population);
        addNewRandomStrategies(NUM_NEW_RANDOMS, population);
        generateNewStrategies(result, populationSize * REPLACEMENT_PERCENTAGE / 100 - NUM_NEW_RANDOMS, population);
        return population;
    }

    private void generateNewStrategies(Table result, int count, List<Strategy> population) {
        Class strategyClass = factory.getStrategyClass();
        List<Strategy> encodedStrategies = population.stream()
                .filter(s -> s.getClass() == strategyClass)
                .collect(Collectors.toList());

        for (Table.Entry e : result.getTopEntries(count)) {
            if (e.getStrategy().getClass() == strategyClass) {
                Strategy a = e.getStrategy();
                Strategy b = encodedStrategies.get(random.nextInt(encodedStrategies.size()));
                population.add(factory.recombine(a, b));
            } else {
                population.add(e.getStrategy());
            }
        }
    }


}
