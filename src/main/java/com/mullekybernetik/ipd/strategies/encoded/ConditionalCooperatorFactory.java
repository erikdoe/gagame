package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.strategies.StrategyFactory;
import com.mullekybernetik.ipd.util.StringGenerator;

import java.util.*;

public class ConditionalCooperatorFactory implements StrategyFactory {

    private final Random random;
    private final StringGenerator g0, g1, g2;

    private int depth;
    private int blockCount;

    public ConditionalCooperatorFactory() {
        this(new Random());
    }

    public ConditionalCooperatorFactory(Random random) {
        this.random = random;
        this.g0 = new StringGenerator("DCN?");
        this.g1 = new StringGenerator("DC?");
        this.g2 = new StringGenerator("DC");
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
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
            String b = g0.randomString(1) + g1.randomString(random.nextInt(depth - 1)) + g2.randomString(1);
//            String b = g0.randomString(random.nextInt(depth) + 1);
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
        String cStringRep = aStringRep.substring(0, xp) + bStringRep.substring(xp, bStringRep.length());

        return (T)new ConditionalCooperator(cStringRep);
    }

}
