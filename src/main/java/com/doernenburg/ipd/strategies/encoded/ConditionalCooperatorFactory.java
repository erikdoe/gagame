package com.doernenburg.ipd.strategies.encoded;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.StrategyFactory;
import com.doernenburg.ipd.util.StringGenerator;

import java.util.*;

public class ConditionalCooperatorFactory implements StrategyFactory {

    private final StringGenerator g0, g1, g2;
    private final Random random;
    private final int depth;
    private final int blockCount;

    private ConditionalCooperatorFactory(Random random, int depth) {
        this.g0 = new StringGenerator("DCN?");
        this.g1 = new StringGenerator("DC?");
        this.g2 = new StringGenerator("DC");
        this.random = random;
        this.depth = depth;
        this.blockCount = 5;
    }

    public ConditionalCooperatorFactory(int depth) {
        this(new Random(), depth);
    }

    public ConditionalCooperatorFactory(Random random) {
        this(random, 3);
    }

    @Override
    public Class getStrategyClass() {
        return ConditionalCooperator.class;
    }

    @Override
    public Collection<Strategy> getRandomStrategies(int count) {
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            strategies.add(createRandomStrategy());
        }
        return strategies;
    }

    private ConditionalCooperator createRandomStrategy() {
        List<String> blocks = new ArrayList<>(blockCount);
        for (int i = 0; i < blockCount; i++) {
            // String must be depth+1 long; take first char from g0, last char from g2, middle chars from g1
            String b = g0.randomString(1) + g1.randomString(random.nextInt(depth - 1)) + g2.randomString(1);
            blocks.add(b);
        }
        return new ConditionalCooperator(String.join("-", blocks));
    }

    @Override
    public <T extends Strategy> T recombine(T a, T b) {

        String aStringRep = ((ConditionalCooperator)a).getStringRepresentation();
        String bStringRep = ((ConditionalCooperator)b).getStringRepresentation();

        if (random.nextBoolean()) {
            String temp = aStringRep;
            aStringRep = bStringRep;
            bStringRep = temp;
        }
        int xp = random.nextInt(Math.min(aStringRep.length(), bStringRep.length()) + 1);
        String cStringRep = aStringRep.substring(0, xp) + bStringRep.substring(xp);

        // a bit of a hack, but we know this is used so that T is actually ConditionalCooperator
        return (T)new ConditionalCooperator(cStringRep);
    }

}
