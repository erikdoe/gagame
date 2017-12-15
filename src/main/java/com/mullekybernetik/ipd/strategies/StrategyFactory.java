package com.mullekybernetik.ipd.strategies;

import java.util.Collection;

public interface StrategyFactory {

    Class getStrategyClass();

    Collection<Strategy> getRandomStrategies(int count);

    <T extends Strategy> T recombine(T a, T b);

}
