package com.mullekybernetik.gagame.strategies;

public class StrategyBase {

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other.toString().equals(toString());
    }

}
