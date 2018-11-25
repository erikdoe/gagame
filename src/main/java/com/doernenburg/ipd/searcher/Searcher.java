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

    public List<Strategy> createInitialPopulation(int populationSize) {
        List<Strategy> population = new ArrayList<>();
        addBasicStrategies(population);
        fillWithRandomStrategiesFromFactory(population, populationSize);
        return population;
    }

    protected void addBasicStrategies(List<Strategy> population) {
        population.add(new Cooperator());
        population.add(new Defector());
        population.add(new Copycat());
        population.add(new Grudger());
        population.add(new Detective());
    }

    protected void fillWithRandomStrategiesFromFactory(List<Strategy> population, int size) {
        int n = size - population.size();
        population.addAll(factory.getRandomStrategies(n));
    }


    public abstract List<Strategy> createNextPopulation(int populationSize, Table result);


    protected Strategy instantiateStrategy(Class c) {
        try {
            return (Strategy)c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null; // keep tools happy
    }
}
