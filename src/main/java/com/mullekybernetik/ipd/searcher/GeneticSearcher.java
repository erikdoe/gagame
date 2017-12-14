package com.mullekybernetik.ipd.searcher;

import com.mullekybernetik.ipd.Searcher;
import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.strategies.StrategyFactory;
import com.mullekybernetik.ipd.strategies.basic.*;
import com.mullekybernetik.ipd.strategies.encoded.ConditionalCooperator;
import com.mullekybernetik.ipd.strategies.encoded.ConditionalCooperatorFactory;
import com.mullekybernetik.ipd.strategies.encoded.DecisionTreeStrategy;
import com.mullekybernetik.ipd.strategies.encoded.DecisionTreeStrategyFactory;
import com.mullekybernetik.ipd.tournament.Table;

import java.util.*;

public class GeneticSearcher implements Searcher {

    private static final int STRATEGY_DEPTH = 4; // TODO: parameters ara all over the place...

    private final Random random;
    private StrategyFactory factory;

    public GeneticSearcher() {
        this.random = new Random();
        this.factory = new ConditionalCooperatorFactory();
    }

    @Override
    public List<Strategy> createInitialPopulation(int populationSize) {
        List<Strategy> strategies = new ArrayList<>();
        for (int i = 0; i < populationSize * 10/100; i++) {
            strategies.add(new Cooperator());
            strategies.add(new Defector());
            strategies.add(new TitForTat());
            strategies.add(new Grudger());
            strategies.add(new Detective());
        }
        fillWithNewRandomStrategies(strategies, populationSize);
        return strategies;
    }

    @Override
    public List<Strategy> createNextPopulation(int populationSize, Table result) {
        List<Strategy> population = new ArrayList<>();
        Map<Class, List<Strategy>> strategyBuckets = new HashMap<>();

        for (Table.Entry e : result.getTopEntries(populationSize * 2 / 3 - 5)) { // remaining 1/3 will be offspring
            Strategy strategy = e.getStrategy();
            List<Strategy> bucket = strategyBuckets.computeIfAbsent(strategy.getClass(), k -> new ArrayList<>());
            bucket.add(strategy);
        }

        for (Class c : strategyBuckets.keySet()) {
            List<Strategy> bucket = strategyBuckets.get(c);
            if (bucket.get(0) instanceof ConditionalCooperator) { // TODO: somehow should know class based on factory
                population.addAll(bucket);
                addOffspring(population, bucket);
            } else {
                population.addAll(bucket);
                for (int i = 0; i < bucket.size() / 2; i++) {
                   population.add(createStrategy(c));
                }
            }
        }
        fillWithNewRandomStrategies(population, populationSize);

        return population;
    }

    private void fillWithNewRandomStrategies(List<Strategy> strategies, int populationSize) {
        int n = populationSize - strategies.size();
        strategies.addAll(factory.createRandomStrategies(n, STRATEGY_DEPTH));
    }

    private void addOffspring(List<Strategy> population, List<Strategy> parents) {
        while (parents.size() > 1) {
            Strategy p0 = parents.remove(0);
            Strategy p1 = parents.remove(random.nextInt(parents.size()));
            population.add(factory.recombine(p0, p1));
        }
    }

    private Strategy createStrategy(Class c) {
        try {
            return (Strategy)c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
