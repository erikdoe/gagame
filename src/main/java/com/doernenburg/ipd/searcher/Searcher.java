package com.doernenburg.ipd.searcher;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;
import com.doernenburg.ipd.strategies.basic.*;
import com.doernenburg.ipd.tournament.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class Searcher {

    protected final StrategyFactory factory;

    protected Searcher(StrategyFactory factory) {
        this.factory = factory;
    }

    public List<Strategy> createInitialPopulation(int targetSize) {
        List<Strategy> population = new ArrayList<>();
        addBasicStrategies(population);
        addNewRandomStrategies(targetSize - population.size(), population);
        return population;
    }

    public abstract List<Strategy> createNextPopulation(int populationSize, Table result);

    protected void addBasicStrategies(List<Strategy> population) {
        population.add(new Cooperator());
        population.add(new Defector());
        population.add(new Copycat());
        population.add(new Grudger());
        population.add(new Detective());
    }

    protected void addNewRandomStrategies(int numNewRandoms, List<Strategy> population) {
        population.addAll(factory.getRandomStrategies(numNewRandoms));
    }

    protected void copyBestStrategies(Table result, int count, List<Strategy> population) {
        for (Table.Entry e : result.getTopEntries(count)) {
            population.add(e.getStrategy());
        }
    }

}
