package com.doernenburg.ipd.searcher;

import com.doernenburg.ipd.tournament.Table;
import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.List;

public class RandomSearcher extends Searcher {

    private static final int REPLACEMENT_PERCENTAGE = 10;

    public RandomSearcher(StrategyFactory factory) {
        super(factory);
    }

    @Override
    public List<Strategy> createNextPopulation(int populationSize, Table result) {
        List<Strategy> population = new ArrayList<>();
        copyBestStrategies(result, populationSize * (100 - REPLACEMENT_PERCENTAGE) / 100, population);
        generateNewStrategies(result, populationSize * REPLACEMENT_PERCENTAGE / 100, population);
        return population;
    }

    private void generateNewStrategies(Table result, int count, List<Strategy> population) {
        for (Table.Entry e : result.getTopEntries(count)) {
            if (e.getStrategy().getClass() == factory.getStrategyClass()) {
                population.addAll(factory.getRandomStrategies(1));
            } else {
                population.add(e.getStrategy());
            }
        }
    }

}
