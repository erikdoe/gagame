package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.strategies.StrategyFactory;
import com.mullekybernetik.ipd.util.StringGenerator;

import java.util.*;

public class ConditionalCooperatorFactory implements StrategyFactory {

    private final Random random;
    private final StringGenerator generator;

    public ConditionalCooperatorFactory() {
        this(new Random());
    }

    public ConditionalCooperatorFactory(Random random) {
        this.random = random;
        this.generator = new StringGenerator("DCN?");
    }

    @Override
    public Collection<Strategy> createRandomStrategies(int count, int depth) {
        Collection<Strategy> strategies = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            strategies.add(createRandomStrategy(depth));
        }
        return strategies;
    }

    private ConditionalCooperator createRandomStrategy(int depth) {
        int nblocks = 5; // random.nextInt(5) + 1;
        List<String> blocks = new ArrayList<>(nblocks);
        for (int i = 0; i < nblocks; i++) {
            String b = generator.randomString(random.nextInt(depth) + 1);
//            b = optimizeBlock(b);
            blocks.add(b);
        }
//        blocks = optimizeStrategy(blocks);
        return new ConditionalCooperator(String.join("-", blocks));
    }

    private static String optimizeBlock(String block) {
        int uidx = block.lastIndexOf('N');
        if (uidx > 0) {
            block = block.substring(uidx, block.length());
        }
        return block;
    }

    private static List<String> optimizeStrategy(List<String> blockList) {
        blockList.sort((block1, block2) -> {
            int r = block1.length() - block2.length();
            if (r != 0) {
                return r;
            }
            return block1.compareTo(block2);
        });
        return blockList;
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
