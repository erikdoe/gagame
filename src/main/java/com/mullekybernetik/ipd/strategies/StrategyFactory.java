package com.mullekybernetik.ipd.strategies;

import java.util.Collection;

public interface StrategyFactory {

    Collection<Strategy> createRandomStrategies(int count, int depth);

    <T extends Strategy> T recombine(T a, T b);

}
