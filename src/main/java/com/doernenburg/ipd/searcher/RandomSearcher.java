package com.doernenburg.ipd.searcher;

import com.doernenburg.ipd.strategies.basic.Cooperator;
import com.doernenburg.ipd.strategies.basic.Copycat;
import com.doernenburg.ipd.strategies.basic.Defector;
import com.doernenburg.ipd.tournament.Table;
import com.doernenburg.ipd.Searcher;
import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.List;

public class RandomSearcher implements Searcher {

    private static final int SURVIVOR_PERCENTAGE = 80;

    private final StrategyFactory factory;

    public RandomSearcher(StrategyFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Strategy> createInitialPopulation(int populationSize) {
        List<Strategy> strategies = new ArrayList<>();
        for (int i = 0; i < populationSize / 2 / 3; i++) {
            strategies.add(new Cooperator());
            strategies.add(new Defector());
            strategies.add(new Copycat());
        }
        fillWithNewRandomStrategies(strategies, populationSize);
        return strategies;
    }

    @Override
    public List<Strategy> createNextPopulation(int populationSize, Table result) {
        List<Strategy> strategies = new ArrayList<>();
        for (Table.Entry e : result.getTopEntries(populationSize * SURVIVOR_PERCENTAGE/100)) {
            strategies.add(e.getStrategy());
        }
        fillWithNewRandomStrategies(strategies, populationSize);
        return strategies;
    }

    private void fillWithNewRandomStrategies(List<Strategy> strategies, int populationSize) {
        int n = populationSize - strategies.size();
        strategies.addAll(factory.getRandomStrategies(n));
    }

}
