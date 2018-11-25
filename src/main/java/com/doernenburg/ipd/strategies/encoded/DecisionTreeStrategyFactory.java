package com.doernenburg.ipd.strategies.encoded;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;
import com.doernenburg.ipd.util.StringGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DecisionTreeStrategyFactory implements StrategyFactory {

    private final StringGenerator generator;
    private final Random random;
    private final int depth;

    private DecisionTreeStrategyFactory(Random random, int depth) {
        this.generator = new StringGenerator("DC");
        this.random = random;
        this.depth = depth;
    }

    public DecisionTreeStrategyFactory(int depth) {
        this(new Random(), depth);
    }

    public DecisionTreeStrategyFactory(Random random) {
        this(random, 3);
    }

    @Override
    public Class getStrategyClass() {
        return DecisionTreeStrategy.class;
    }

    @Override
    public Collection<Strategy> getRandomStrategies(int count) {
        int size = (1 << depth + 1) - 1;
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            strategies.add(new DecisionTreeStrategy(generator.randomString(size)));
        }
        return strategies;
    }

    @Override
    public <T extends Strategy> T recombine(T a, T b) {

        String aStringRep = ((DecisionTreeStrategy)a).getStringRepresentation();
        String bStringRep = ((DecisionTreeStrategy)b).getStringRepresentation();

        int xp = random.nextInt(aStringRep.length() + 1);
        if (random.nextBoolean()) {
            String temp = aStringRep;
            aStringRep = bStringRep;
            bStringRep = temp;
        }
        String cStringRep = aStringRep.substring(0, xp) + bStringRep.substring(xp, aStringRep.length());

        return (T)new DecisionTreeStrategy(cStringRep);
    }

}
