package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.strategies.StrategyFactory;
import com.mullekybernetik.ipd.util.StringGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DecisionTreeStrategyFactory implements StrategyFactory {

    private final Random random;
    private final StringGenerator generator;

    private int depth;

    public DecisionTreeStrategyFactory() {
        this(new Random());
    }

    public DecisionTreeStrategyFactory(Random random) {
        this.random = random;
        this.generator = new StringGenerator("DC");
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public Class getStrategyClass() {
        return DecisionTreeStrategy.class;
    }

    @Override
    public Collection<Strategy> getRandomStrategies(int count) {
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int size = (1 << depth + 1) - 1; //   (1 << (random.nextInt(depth) + 2)) - 1;
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
